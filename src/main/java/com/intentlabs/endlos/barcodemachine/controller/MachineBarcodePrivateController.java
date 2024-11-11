/*******************************************************************************
 * Copyright -2019 @intentlabs
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.intentlabs.endlos.barcodemachine.controller;

import com.intentlabs.common.aop.AccessLog;
import com.intentlabs.common.aop.Authorization;
import com.intentlabs.common.controller.AbstractController;
import com.intentlabs.common.enums.ResponseCode;
import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.file.enums.ExcelFileExtensionEnum;
import com.intentlabs.common.file.operation.FileOperation;
import com.intentlabs.common.file.service.FileService;
import com.intentlabs.common.logger.LoggerService;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.CommonResponse;
import com.intentlabs.common.response.Response;
import com.intentlabs.common.user.enums.ModuleEnum;
import com.intentlabs.common.user.enums.RightsEnum;
import com.intentlabs.endlos.barcodemachine.operation.MachineBarcodeItemOperation;
import com.intentlabs.endlos.barcodemachine.operation.MachineBarcodeOperation;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeItemService;
import com.intentlabs.endlos.barcodemachine.service.MachineBarcodeService;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeItemView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * This controller maps all Machine related apis.
 *
 * @author Milan Gohil
 * @since 17/8/2023
 */
@Controller
@RequestMapping("/private/machine-barcode")
public class MachineBarcodePrivateController extends AbstractController<MachineBarcodeFileView> {

    @Autowired
    FileOperation fileOperation;
    @Autowired
    MachineBarcodeItemOperation machineBarcodeItemOperation;
    @Autowired
    MachineBarcodeService machineBarcodeService;
    @Autowired
    MachineBarcodeItemService machineBarcodeItemService;
    @Autowired
    private MachineBarcodeOperation machineBarcodeOperation;
    @Autowired
    private FileService fileService;

    @Override
    public BaseOperation<MachineBarcodeFileView> getOperation() {
        return machineBarcodeOperation;
    }

    /*@PostMapping("/upload-machine-barcode")
    @ResponseBody
    @AccessLog
    public Response uploadDataTableExcelFile(@RequestParam(name = "file", required = true) MultipartFile multipartFile, @RequestParam(name = "barcodeFileName") String barcodeFileName) throws EndlosAPIException, IOException, ExecutionException, InterruptedException {

        isValidExcelFile(multipartFile);

        //For 26000000 kb is 26 mb
        if (multipartFile.getSize() > 26000000) {
            throw new EndlosAPIException(ResponseCode.File_Size_Exceed.getCode(), ResponseCode.File_Size_Exceed.getMessage());
        }
        return machineBarcodeOperation.doFileUpload(multipartFile, barcodeFileName);
    }*/

    @PostMapping("/upload-machine-barcode")
    @ResponseBody
    @AccessLog
    public Response uploadDataTableExcelFile(@RequestParam(name = "file", required = true) MultipartFile multipartFile, @RequestParam(name = "barcodeFileName") String barcodeFileName) throws EndlosAPIException, IOException, ExecutionException, InterruptedException {
        //return machineBarcodeOperation.doExport(id);

        isValidExcelFile(multipartFile);

        //For 26000000 kb is 26 mb
        if (multipartFile.getSize() > 26000000) {
            throw new EndlosAPIException(ResponseCode.File_Size_Exceed.getCode(), ResponseCode.File_Size_Exceed.getMessage());
        }

        CompletableFuture<Response> future = machineBarcodeOperation.doFileUpload(multipartFile, barcodeFileName);

        return future.thenApply(response -> {
            return response;
        }).exceptionally(ex -> {
            return CommonResponse.create(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Failed to export file: " + ex.getMessage());
        }).join();

    }

    @PostMapping("/update-upload-machine-barcode")
    @ResponseBody
    @AccessLog
    @Authorization(modules = ModuleEnum.MACHINE_BARCODE, rights = RightsEnum.UPDATE)
    public Response updateBarcodeMachine(@RequestParam(name = "id") Long id, @RequestParam(name = "file", required = false) MultipartFile multipartFile) throws EndlosAPIException, IOException, ExecutionException, InterruptedException {
        if (id == null) {
            throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
        }
        isValidExcelFile(multipartFile);

        if (multipartFile.getSize() > 26000000) {
            throw new EndlosAPIException(ResponseCode.File_Size_Exceed.getCode(), ResponseCode.File_Size_Exceed.getMessage());
        }
        return machineBarcodeOperation.doUpdateFile(id, multipartFile);
    }


    @Override
    @AccessLog
    @Authorization(modules = ModuleEnum.MACHINE_BARCODE, rights = RightsEnum.UPDATE)
    public Response edit(@RequestParam(name = "id") Long id) throws EndlosAPIException {
        if (id == null) {
            throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
        }
        return machineBarcodeOperation.doEdit(id);
    }

    private void isValidExcelFile(MultipartFile multipartFile) throws EndlosAPIException {
        if (multipartFile == null || (multipartFile != null && multipartFile.isEmpty())) {
            throw new EndlosAPIException(ResponseCode.FILE_IS_MISSING.getCode(), ResponseCode.FILE_IS_MISSING.getMessage());
        }
        if (ExcelFileExtensionEnum.CSV.fromId(multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".") + 1)) == null) {
            throw new EndlosAPIException(ResponseCode.UPLOAD_CSV_ONLY.getCode(), ResponseCode.UPLOAD_CSV_ONLY.getMessage());
        }
    }

    @Override
    @AccessLog
    @Authorization(modules = ModuleEnum.MACHINE_BARCODE, rights = RightsEnum.LIST)
    public Response search(@RequestBody MachineBarcodeFileView machineBarcodeFileView,
                           @RequestParam(name = "start", required = true) Integer start,
                           @RequestParam(name = "recordSize", required = true) Integer recordSize,
                           @RequestParam(name = "orderType", required = false) Integer orderType,
                           @RequestParam(name = "orderParam", required = false) Integer orderParam) throws EndlosAPIException {

        CompletableFuture<Response> future = CompletableFuture.supplyAsync(() -> {
            try {
                return machineBarcodeOperation.doSearch(machineBarcodeFileView, start, recordSize, orderType, orderParam);
            } catch (EndlosAPIException e) {
                throw new RuntimeException(e);
            }
        });
        return future.join();
    }

    @Override
    @AccessLog
    @Authorization(modules = ModuleEnum.MACHINE_BARCODE, rights = RightsEnum.LIST)
    public Response view(@RequestParam(name = "id") Long id) throws EndlosAPIException {
        if (id == null) {
            throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
        }

        CompletableFuture<Response> future = CompletableFuture.supplyAsync(() -> {
            try {

                /*MachineBarcodeFileView machineBarcodeFileView = new MachineBarcodeFileView();
                machineBarcodeFileView.setId(id);

                MachineBarcodeItemView machineBarcodeItemView = new MachineBarcodeItemView();
                machineBarcodeItemView.setMachineBarcodeFileView(machineBarcodeFileView);*/

                return machineBarcodeOperation.doView(id);

            } catch (EndlosAPIException e) {
                throw new RuntimeException(e);
            }
        });
        return future.join();

    }

    @RequestMapping(value = "/machine-barcode-item", method = RequestMethod.POST)
    @ResponseBody
    @AccessLog
    @Authorization(modules = ModuleEnum.MACHINE_BARCODE, rights = RightsEnum.LIST)
    public Response machineBarcodeItem(@RequestBody MachineBarcodeFileView machineBarcodeFileView,
                                        @RequestParam(name = "start", required = true) Integer start,
                                        @RequestParam(name = "recordSize", required = true) Integer recordSize) throws EndlosAPIException {
        MachineBarcodeItemView machineBarcodeItemView = new MachineBarcodeItemView();
        machineBarcodeItemView.setBarcodeName(machineBarcodeFileView.getFullTextSearch());
        machineBarcodeItemView.setMachineBarcodeFileView(machineBarcodeFileView);
        return machineBarcodeItemOperation.doSearch(machineBarcodeItemView, start, recordSize, 1, 2);
    }

    @GetMapping("/dropdown")
    @ResponseBody
    @AccessLog
    public Response dropdown() {
        return machineBarcodeOperation.doDropdown();
    }

    @Override
    public Response add() throws EndlosAPIException {
        return null;
    }


    @Override
    public void isValidSaveData(MachineBarcodeFileView view) throws EndlosAPIException {

    }

    @Override
    @AccessLog
    @Authorization(modules = ModuleEnum.MACHINE_BARCODE, rights = RightsEnum.DELETE)
    public Response delete(@RequestParam(name = "id", required = true) Long id) throws EndlosAPIException {
        if (id == null) {
            throw new EndlosAPIException(ResponseCode.INVALID_REQUEST.getCode(), ResponseCode.INVALID_REQUEST.getMessage());
        }
        try {
            return machineBarcodeOperation.doDelete(id);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            LoggerService.exception(dataIntegrityViolationException);
            throw new EndlosAPIException(ResponseCode.CANT_DELETE_BARCODE_FILE.getCode(), ResponseCode.CANT_DELETE_BARCODE_FILE.getMessage());
        }
    }

    /**
     * This method is used to export Barcodes excel.
     *
     * @param id
     * @return
     * @throws EndlosAPIException
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    @ResponseBody
    @AccessLog
    @Authorization(modules = ModuleEnum.MACHINE_BARCODE, rights = RightsEnum.DOWNLOAD)
    Response export(@RequestParam(name = "id") Long id) throws EndlosAPIException {
        //return machineBarcodeOperation.doExport(id);

        CompletableFuture<Response> future = machineBarcodeOperation.doExportAsync(id);

        return future.thenApply(response -> {
            return response;
        }).exceptionally(ex -> {
            return CommonResponse.create(ResponseCode.INTERNAL_SERVER_ERROR.getCode(), "Failed to export file: " + ex.getMessage());
        }).join();

    }
}