package com.pra.payrollmanager.user.stock.book;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pra.payrollmanager.base.BaseControl;
import com.pra.payrollmanager.exception.AnyThrowable;
import com.pra.payrollmanager.exception.unchecked.DataNotFoundEx;
import com.pra.payrollmanager.exception.unchecked.DuplicateDataEx;
import com.pra.payrollmanager.response.dto.Response;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping("stock-book")
public class StockBookControl extends BaseControl<StockBookService> {

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<List<StockBookDTO>> getAllStocks() {
		return Response.payload(service.getAll());
	}

	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<StockBookDTO> getStockFor(@PathVariable("id") @NotNull String productId)
			throws DataNotFoundEx, AnyThrowable {
		return Response.payload(service.getById(productId));
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<StockBookDTO> createUser(@Valid @RequestBody StockBookDTO stock)
			throws DuplicateDataEx, AnyThrowable {
		return Response.payload(service.create(stock));
	}

	@PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Response<StockBookDTO> updateStock(@Valid @RequestBody StockBookDTO stock)
			throws AnyThrowable, DataNotFoundEx {
		return Response.payload(service.update(stock));
	}

}
