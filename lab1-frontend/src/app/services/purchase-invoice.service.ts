import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Invoice} from "../common/invoice";
import {InvoiceProduct} from "../common/invoice-product";
import {ClientVendorDto} from "../common/client-vendor-dto";

@Injectable({
  providedIn: 'root'
})
export class PurchaseInvoiceService {
  private apiUrl = 'http://localhost:8080/api/purchase-invoice'; // Base URL for the API

  constructor(private http: HttpClient) {}

  // Method to create HTTP headers with authorization token
  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('accessToken');
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // Method to create a new invoice
  createToPurchaseInvoice(): Observable<Invoice> {
    return this.http.get<Invoice>(`${this.apiUrl}/createNewInvoice`,  {
      headers: this.getHeaders()
    });
  }

  // Method to get a list of purchase invoices
  getPurchasesInvoiceList(): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.apiUrl}/list`, {
      headers: this.getHeaders()
    });
  }

  // Method to get an invoice by ID
  findInvoiceById(id: number): Observable<Invoice> {
    return this.http.get<Invoice>(`${this.apiUrl}/list/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to list invoice products by invoice ID
  listInvoiceProduct(id: number): Observable<InvoiceProduct[]> {
    return this.http.get<InvoiceProduct[]>(`${this.apiUrl}/list-invoiceproduct/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to list client vendors
  listClientVendors(): Observable<ClientVendorDto[]> {
    return this.http.get<ClientVendorDto[]>(`${this.apiUrl}/list-clientvendor`, {
      headers: this.getHeaders()
    });
  }

  // Method to create an invoice
  createInvoice(invoiceDto: Invoice): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/create-invoice`, invoiceDto, {
      headers: this.getHeaders()
    });
  }

  // Method to get invoices for a company
  getInvoicesForACompany(): Observable<Invoice[]> {
    return this.http.get<Invoice[]>(`${this.apiUrl}/invoices-of-company`, {
      headers: this.getHeaders()
    });
  }

  // Method to find invoice products by invoice ID
  findInvoiceProductsByInvoiceId(id: number): Observable<InvoiceProduct[]> {
    return this.http.get<InvoiceProduct[]>(`${this.apiUrl}/invoiceProducts/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to add a product to a purchase invoice
  addProductToPurchaseInvoice(id: number, invoiceProductDto: InvoiceProduct): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/addInvoiceProduct/${id}`, invoiceProductDto, {
      headers: this.getHeaders()
    });
  }

  // Method to update a purchase invoice
  updatePurchaseInvoice(id: number, invoiceDto: Invoice): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/update/${id}`, invoiceDto, {
      headers: this.getHeaders()
    });
  }

  // Method to delete a purchase invoice
  deletePurchaseInvoice(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/delete/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to approve a purchase invoice
  approvePurchaseInvoice(id: number): Observable<void> {
    return this.http.get<void>(`${this.apiUrl}/approve/${id}`, {
      headers: this.getHeaders()
    });
  }

  // Method to remove a product from a purchase invoice
  removePurchaseInvoiceProduct(invoiceId: number, invoiceProductId: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/removeInvoiceProduct/${invoiceId}/${invoiceProductId}`, {
      headers: this.getHeaders()
    });
  }
}
