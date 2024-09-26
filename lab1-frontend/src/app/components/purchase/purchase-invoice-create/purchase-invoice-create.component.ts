import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {Router, RouterLink} from "@angular/router";
import {ClientVendorService} from "../../../services/client-vendor.service";
import {ClientVendorDto} from "../../../common/client-vendor-dto";
import {NgForOf, NgIf} from "@angular/common";
import {PurchaseInvoiceService} from "../../../services/purchase-invoice.service";
import {Invoice} from "../../../common/invoice";
import {InvoiceProduct} from "../../../common/invoice-product";
import {ProductService} from "../../../services/product.service";
import {InvoiceStatus} from "../../../enums/invoice-status";
import {InvoiceType} from "../../../enums/invoice-type";
import {Product} from "../../../common/product";

@Component({
  selector: 'app-purchase-invoice-create',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf,
    RouterLink
  ],
  templateUrl: './purchase-invoice-create.component.html',
  styleUrl: './purchase-invoice-create.component.css'
})
export class PurchaseInvoiceCreateComponent implements OnInit{

  invoiceForm: FormGroup;
  newInvoiceProductForm: FormGroup;
  vendors:ClientVendorDto[] = [];
  products:Product[] = [];
  invoiceProducts = [];
  isVendorSelected = false;

  constructor(
    private fb: FormBuilder,
    private vendorService: ClientVendorService,
    private productService: ProductService,
    private invoiceService: PurchaseInvoiceService
  ) {
    this.invoiceForm = this.fb.group({
      invoiceNo: [{ value: '', disabled: true }],
      date: [{ value: '', disabled: true }],
      clientVendor: ['', Validators.required]
    });

    this.newInvoiceProductForm = this.fb.group({
      product: ['', Validators.required],
      quantity: [1, Validators.required],
      price: [0, Validators.required],
      tax: [0, Validators.required]
    });
  }

  ngOnInit(): void {
    this.initForms();
    this.loadVendors();
  }

  private initForms() {
    this.invoiceForm = this.fb.group({
      invoiceNo: [{ value: '', disabled: true }],
      date: [{ value: '', disabled: true }],
      clientVendor: ['', Validators.required]
    });

    this.newInvoiceProductForm = this.fb.group({
      product: ['', Validators.required],
      quantity: [1, Validators.required],
      price: [0, Validators.required],
      tax: [0, Validators.required]
    });
  }

  private loadVendors() {
    this.vendorService.getAllClientVendors().subscribe(vendors => {
      this.vendors = vendors;
    });
  }

  onVendorChange(vendorId: string) {
    if (vendorId) {
      this.productService.getProducts().subscribe(products => {
        this.products = products;
        this.isVendorSelected = true;
      });
    } else {
      this.isVendorSelected = false;
    }
  }

  addInvoiceProduct() {
    if (this.newInvoiceProductForm.valid) {
      const product: InvoiceProduct = this.newInvoiceProductForm.value;

      const total = (product.price * product.quantity) + ((product.price * product.quantity) * (product.tax / 100));



      this.newInvoiceProductForm.reset();
    }
  }

  removeInvoiceProduct(productId: number) {
  }

  onSubmit() {
    if (this.invoiceForm.valid) {
      const invoiceData = {
        ...this.invoiceForm.value,
        products: this.invoiceProducts
      };
      this.isVendorSelected=true;
      this.invoiceService.createToPurchaseInvoice().subscribe(response => {
        console.log(response)
      });
    }
  }

  onReset() {
    this.invoiceForm.reset();
    this.newInvoiceProductForm.reset();
    this.invoiceProducts = [];
    this.isVendorSelected = false;
  }

}
