import {Component, OnInit} from '@angular/core';
import {Router, RouterLink} from "@angular/router";
import {NgForOf, NgIf} from "@angular/common";
import {CategoryService} from "../../services/category.service";
import {CategoryDto} from "../../common/category-dto";

@Component({
  selector: 'app-category-list',
  standalone: true,
  imports: [
    RouterLink,
    NgIf,
    NgForOf
  ],
  templateUrl: './category-list.component.html',
  styleUrl: './category-list.component.css'
})
export class CategoryListComponent implements OnInit{

  constructor(private categoryService:CategoryService,private router: Router) {
  }

  categories:CategoryDto[] = [];


  ngOnInit(): void {
    this.categoryService.getAllCategories().subscribe(data=>{
      this.categories=data;
    })
  }

}
