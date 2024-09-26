import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {CategoryDto} from "../common/category-dto";

@Injectable({
  providedIn: 'root'
})
export class CategoryService {
  private apiUrl = 'http://localhost:8080/api/categories'

  constructor(private http:HttpClient) { }
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }
  // Method to get all categories
  getAllCategories(): Observable<CategoryDto[]> {
    return this.http.get<CategoryDto[]>(`${this.apiUrl}/list`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a new category
  createCategory(categoryDto: CategoryDto): Observable<any> {
    return this.http.post<void>(`${this.apiUrl}/create`, categoryDto, {
      headers: this.getHeaders()
    });
  }

  // Method to update an existing category
  editCategory(id: number, categoryDto: CategoryDto): Observable<any> {
    return this.http.put<void>(`${this.apiUrl}/edit/${id}`, categoryDto, {
      headers: this.getHeaders()
    });
  }

  // Method to delete a category by ID
  deleteCategory(id: number): Observable<any> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, {
      headers: this.getHeaders()
    });
  }
}
