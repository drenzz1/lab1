import {InvoiceStatus} from "../enums/invoice-status";
import {InvoiceType} from "../enums/invoice-type";
import {CompanyDto} from "./company";
import {ClientVendorDto} from "./client-vendor-dto";
import {InvoiceProduct} from "./invoice-product";

export class Invoice {
  constructor(
    public id: number,
    public invoiceNr: string,
    public invoiceStatus: InvoiceStatus,
    public invoiceType: InvoiceType,
    public invoiceDate: Date, // Use JavaScript Date for LocalDate
    public companyDto: CompanyDto,
    public clientVendorDto: ClientVendorDto,
    public price: number, // Use number for BigDecimal
    public tax: number,
    public total: number,
    public invoiceProducts:InvoiceProduct[]
  ) {}
}
