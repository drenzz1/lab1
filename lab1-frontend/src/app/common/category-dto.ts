import {CompanyDto} from "./company";

export class CategoryDto {
  constructor(
    public id: number,
    public description: string,
    public hasProduct:boolean,
    public companyDto: CompanyDto

  ) {}
}
