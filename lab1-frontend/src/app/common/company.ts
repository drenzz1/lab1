import {CompanyStatus} from "../enums/company-status";


export class CompanyDto {
  constructor(
    public id: number,
    public title: string,
    public phone: string,
    public website: string,
    public addressLine1: string,
    public addressLine2: string,
    public city: string,
    public state: string,
    public country: string,
    public zipCode: string,
    public companyStatus: CompanyStatus
  ) {}
}
