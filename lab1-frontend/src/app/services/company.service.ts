import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {CompanyDto} from "../common/company";

@Injectable({
  providedIn: 'root'
})
export class CompanyService {
  private apiUrl = 'http://localhost:8080/api/company'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to get all companies
  getAllCompanies(): Observable<CompanyDto[]> {
    return this.http.get<CompanyDto[]>(`${this.apiUrl}/list`, {
      headers: this.getHeaders()
    });
  }

  // Method to create a new company
  createNewCompany(companyDto: CompanyDto): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/create`, companyDto, {
      headers: this.getHeaders()
    });
  }

  // Method to get a company by ID
  getCompanyById(id: number): Observable<CompanyDto> {
    return this.http.get<CompanyDto>(`${this.apiUrl}/list/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to update an existing company
  editCompany(id: number, companyDto: CompanyDto): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/edit/${id}`, companyDto, {
      headers: this.getHeaders()
    });
  }

  // Method to activate a company
  activateCompany(id: number): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/activate/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to deactivate a company
  deactivateCompany(id: number): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/deactivate/${id}`, {
      headers: this.getHeaders()
    });
  }
}
