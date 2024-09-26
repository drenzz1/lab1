class InvoiceDto {
}

class ProductDto {
}

export class InvoiceProduct {
  constructor(
    public id: number,
    public quantity: number,
    public price: number, // Use number for BigDecimal
    public tax: number, // Assuming tax is an integer percentage
    public total: number, // Use number for BigDecimal
    public profitLoss: number, // Use number for BigDecimal
    public remainingQuantity: number,
    public invoiceDto: InvoiceDto,
    public productDto: ProductDto
  ) {}
}
