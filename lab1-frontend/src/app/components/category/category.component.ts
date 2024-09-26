import {Component, OnInit} from '@angular/core';
import {Route, Router, RouterLink} from "@angular/router";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {CategoryService} from "../../services/category.service";

@Component({
  selector: 'app-category',
  standalone: true,
  imports: [
    RouterLink,
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './category.component.html',
  styleUrl: './category.component.css'
})
export class CategoryComponent implements OnInit{

  categoryForm: FormGroup;

  constructor(private fb: FormBuilder,private categoryService:CategoryService,private router:Router) {
    this.categoryForm = this.fb.group({
      description: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.categoryForm = this.fb.group({
      description: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.categoryForm.valid) {
      this.categoryService.createCategory(this.categoryForm.value).subscribe({
        next: () => this.router.navigate(['/layout/category']),
        error: (err) => console.error('Error creating company', err)
      })
      console.log(this.categoryForm.value);
      // Handle form submission, e.g., send data to a server
    }
  }

  onReset(): void {
    this.categoryForm.reset();
  }
}
