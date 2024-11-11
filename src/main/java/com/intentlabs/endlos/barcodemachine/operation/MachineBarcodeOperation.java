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
package com.intentlabs.endlos.barcodemachine.operation;

import com.intentlabs.common.exception.EndlosAPIException;
import com.intentlabs.common.operation.BaseOperation;
import com.intentlabs.common.response.Response;
import com.intentlabs.endlos.barcodemachine.view.MachineBarcodeFileView;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author Milan Gohil.
 * @version 1.0
 * @since 23/08/2023
 */
public interface MachineBarcodeOperation extends BaseOperation<MachineBarcodeFileView> {
    Response doDropdown();

    //Response doFileUpload(MultipartFile multipartFile, String barcodeFileName) throws EndlosAPIException, IOException, ExecutionException, InterruptedException;
    Response doUpdateFile(Long id, MultipartFile multipartFile) throws IOException, ExecutionException, InterruptedException;
    Response doExport(Long id) throws EndlosAPIException;

    CompletableFuture<Response> doExportAsync(Long id);

    CompletableFuture<Response> doFileUpload(MultipartFile multipartFile, String barcodeFileName) throws ExecutionException, InterruptedException, EndlosAPIException;
}