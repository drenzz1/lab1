export class AddressDto {
  constructor(
    public id: number,
    public addressLine1: string,
    public addressLine2: string,
    public city: string,
    public state: string,
    public country: string,
    public zipCode: string
  ) {}
}
