import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {Product} from "../../common/product";
import {ProductUnit} from "../../enums/product-unit";
import {CategoryDto} from "../../common/category-dto";
import {CompanyDto} from "../../common/company";
import {CompanyStatus} from "../../enums/company-status";
import {AddressDto} from "../../common/address-dto";

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    NgForOf
  ],
  templateUrl: './product-list.component.html',
  styleUrl: './product-list.component.css'
})
export class ProductListComponent implements OnInit{
  products: Product[] = [];
  ProductUnit = ProductUnit; // Make enum accessible in the template

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    // Fetch the products from an API or a service
    // For demonstration, we'll use a static list
    this.products = [
      {
        id: 1,
        categoryDto: new CategoryDto(
          1,
          'Electronics',
          false,
          new CompanyDto(
            1,
            'Tech Corp',
            '123-456-7890',
            'www.techcorp.com',
            '123 Tech Lane', 'Tech City', 'Tech State', '12345','asef','asefas',
            CompanyStatus.ACTIVE
          ),

        ),
        name: 'Smartphone',
        productUnit: ProductUnit.PCS,
        lowLimitAlert: 5,
        quantityInStock: 10
      },
      {
        id: 2,
        categoryDto: new CategoryDto(
          2,
          'Books',
          false,
          new CompanyDto(
            2,
            'Book World',
            '098-765-4321',
            'www.bookworld.com',
            '456 Book Road', 'Booktown', 'Book State', '54321','asef','asefs',
            CompanyStatus.PASSIVE
          )
        ),
        name: 'Angular Book',
        productUnit: ProductUnit.PCS,
        lowLimitAlert: 2,
        quantityInStock: 0
      }
      // Add more products as needed
    ];
  }

  editProduct(id: number): void {
    this.router.navigate(['/products/update', id]);
  }

  deleteProduct(id: number): void {
    console.log(`Delete product with ID: ${id}`);
    // Implement deletion logic here
  }
}
