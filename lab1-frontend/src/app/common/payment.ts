import {Months} from "../enums/months";
import {CompanyDto} from "./company";

export class Payment {
  constructor(
    public id: number,
    public year: number,
    public month: Months,
    public paymentDate: Date, // Use JavaScript Date for LocalDate
    public amount: number, // Use number for BigDecimal
    public isPaid: boolean,
    public companyCC: string,
    public description: string,
    public companyDto: CompanyDto
  ) {}
}
