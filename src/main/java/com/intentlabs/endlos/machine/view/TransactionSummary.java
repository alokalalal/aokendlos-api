package com.intentlabs.endlos.machine.view;

import java.math.BigDecimal;

public class TransactionSummary {
	private Long totalPatBottleCount;
	private Long totalAluBottleCount;
	private Long totalGlassBottleCount;
	private BigDecimal totalPatBottleValue;
	private BigDecimal totalAluBottleValue;
	private BigDecimal totalGlassBottleValue;
	private BigDecimal totalValue;
	private Long totalBottle;

	private Long totalVoucher;

	public Long getTotalVoucher() {
		return totalVoucher;
	}

	public void setTotalVoucher(Long totalVoucher) {
		this.totalVoucher = totalVoucher;
	}

	public Long getTotalPatBottleCount() {
		return totalPatBottleCount;
	}

	public void setTotalPatBottleCount(Long totalPatBottleCount) {
		this.totalPatBottleCount = totalPatBottleCount;
	}

	public Long getTotalAluBottleCount() {
		return totalAluBottleCount;
	}

	public void setTotalAluBottleCount(Long totalAluBottleCount) {
		this.totalAluBottleCount = totalAluBottleCount;
	}

	public Long getTotalGlassBottleCount() {
		return totalGlassBottleCount;
	}

	public void setTotalGlassBottleCount(Long totalGlassBottleCount) {
		this.totalGlassBottleCount = totalGlassBottleCount;
	}

	public BigDecimal getTotalPatBottleValue() {
		return totalPatBottleValue;
	}

	public void setTotalPatBottleValue(BigDecimal totalPatBottleValue) {
		this.totalPatBottleValue = totalPatBottleValue;
	}

	public BigDecimal getTotalAluBottleValue() {
		return totalAluBottleValue;
	}

	public void setTotalAluBottleValue(BigDecimal totalAluBottleValue) {
		this.totalAluBottleValue = totalAluBottleValue;
	}

	public BigDecimal getTotalGlassBottleValue() {
		return totalGlassBottleValue;
	}

	public void setTotalGlassBottleValue(BigDecimal totalGlassBottleValue) {
		this.totalGlassBottleValue = totalGlassBottleValue;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
	}

	public Long getTotalBottle() {
		return totalBottle;
	}

	public void setTotalBottle(Long totalBottle) {
		this.totalBottle = totalBottle;
	}
}
