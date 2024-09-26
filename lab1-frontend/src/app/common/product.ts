import {ProductUnit} from "../enums/product-unit";
import {CategoryDto} from "./category-dto";

export class Product {
  constructor(
    public id: number,
    public name: string,
    public quantityInStock: number,
    public lowLimitAlert: number,
    public productUnit: ProductUnit,
    public categoryDto: CategoryDto
  ) {}
}
