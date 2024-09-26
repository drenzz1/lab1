import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ProductUnit} from '../../enums/product-unit';
import {CategoryDto} from "../../common/category-dto";
import {ProductService} from "../../services/product.service";
import {CategoryService} from "../../services/category.service";
import {CompanyDto} from "../../common/company";
import {CompanyStatus} from "../../enums/company-status";
import {Product} from "../../common/product";
import {User} from "../../common/user";

@Component({
  selector: 'app-product-create',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    NgForOf,
    ReactiveFormsModule
  ],
  templateUrl: './product-create.component.html',
  styleUrl: './product-create.component.css'
})
export class ProductCreateComponent implements OnInit{

  productForm: FormGroup;
  categories :CategoryDto[]= [];
  productUnits = Object.values(ProductUnit);
  selectedCategory:CategoryDto=new CategoryDto(0,'',true, new CompanyDto(0,'','','','','','','','','',CompanyStatus.ACTIVE))

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private productService:ProductService,
    private categoryService:CategoryService
  ) {
    this.productForm = this.fb.group({
      category: ['', Validators.required],
      name: ['', [Validators.required, Validators.minLength(3)]],
      quantityInStock :['', Validators.required],
      lowLimitAlert: ['', [Validators.required, Validators.min(1)]],
      productUnit: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.productForm = this.fb.group({
      category: ['', Validators.required],
      name: ['', [Validators.required, Validators.minLength(3)]],
      quantityInStock :['', Validators.required],
      lowLimitAlert: ['', [Validators.required, Validators.min(1)]],
      productUnit: ['', Validators.required]
    });

    this.categoryService.getAllCategories().subscribe(data=>{
      this.categories=data;
    })
  }

  onSubmit(): void {
    if (this.productForm.valid) {

      for (let i = 0; i < this.categories.length; i++) {
        if (this.categories[i].id == this.productForm.get('category')?.value) {
          this.selectedCategory = this.categories[i];
          break; // Exit the loop once the role is found
        }
      }


      const productDto = new Product(
        0,
        this.productForm.get('name')?.value,
        this.productForm.get('quantityInStock')?.value,
        this.productForm.get('lowLimitAlert')?.value,
        this.productForm.get('productUnit')?.value,
        this.selectedCategory


      );

      console.log(productDto)
      this.productService.createProduct(productDto).subscribe({
        next: () => this.router.navigate(['/layout/product']),
        error: (err) => console.error('Error creating company', err)
      })

      // Handle form submission
      console.log('Form Submitted', this.productForm.value);
      // Navigate to a different page or show a success message
    }
  }

  onReset(): void {
    this.productForm.reset();
  }

  getErrors(controlName: string): string[] {
    const control = this.productForm.get(controlName);
    if (control && control.errors) {
      return Object.keys(control.errors).map(key => {
        switch (key) {
          case 'required':
            return 'This field is required';
          case 'minlength':
            return `Minimum length is ${control.errors}['minlength'].requiredLength}`;
          case 'min':
            return 'Value must be greater than zero';
          default:
            return 'Invalid field';
        }
      });
    }
    return [];
  }

}
