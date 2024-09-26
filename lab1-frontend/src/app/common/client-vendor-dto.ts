import {ClientVendorType} from "../enums/client-vendor-type";
import {AddressDto} from "./address-dto";
import {CompanyDto} from "./company";


export class ClientVendorDto {
  constructor(
    public id: number,
    public clientVendorName: string,
    public phone: string,
    public website: string,
    public clientVendorType: ClientVendorType,
    public addressLine1: string,
    public addressLine2: string,
    public city: string,
    public state: string,
    public country: string,
    public zipCode: string,
    public companyDto: CompanyDto
  ) {}
}
