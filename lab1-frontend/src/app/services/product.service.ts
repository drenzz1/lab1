import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Product} from "../common/product";

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private apiUrl = 'http://localhost:8080/api/product'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get all products for the company
  getProducts(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}/list`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a new product
  createProduct(productDto: Product): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/create`, productDto, {
      headers: this.getHeaders()
    });
  }

  // Method to update an existing product
  editProduct(id: number, productDto: Product): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/edit/${id}`, productDto, {
      headers: this.getHeaders()
    });
  }

  // Method to delete a product by ID
  deleteProduct(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to get products in stock for the company
  getProductsInStockForCompany(): Observable<Product[]> {
    return this.http.get<Product[]>(`${this.apiUrl}`, {
      headers: this.getHeaders()
    });
  }
}
