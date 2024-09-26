import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {ProductService} from "../../../services/product.service";
import {Router} from "@angular/router";
import {PurchaseInvoiceService} from "../../../services/purchase-invoice.service";
import {ClientVendorService} from "../../../services/client-vendor.service";
import {InvoiceProduct} from "../../../common/invoice-product";
import {Invoice} from "../../../common/invoice";
import {NgForOf, NgIf} from "@angular/common";


@Component({
  selector: 'app-purchase-invoice-update',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './purchase-invoice-update.component.html',
  styleUrl: './purchase-invoice-update.component.css'
})
export class PurchaseInvoiceUpdateComponent implements OnInit{

  invoiceForm: FormGroup;
  newInvoiceProductForm: FormGroup;
  vendors: any[] = [];
  products: any[] = [];
  invoice: Invoice[]=[]; // Replace with your actual Invoice type
  invoiceProducts: InvoiceProduct[] = []; // Replace with your actual InvoiceProduct type

  constructor(
    private fb: FormBuilder,
    private invoiceService: PurchaseInvoiceService,
    private clientVendorsService:ClientVendorService,
    private productService: ProductService,
    private router: Router
  ) {
    this.invoiceForm = this.fb.group({
      invoiceNo: [{ value: '', disabled: true }, Validators.required],
      date: [{ value: '', disabled: true }, Validators.required],
      clientVendor: ['', Validators.required]
    });

    this.newInvoiceProductForm = this.fb.group({
      product: ['', Validators.required],
      quantity: [1, [Validators.required, Validators.min(1)]],
      price: [0, [Validators.required, Validators.min(0)]],
      tax: [0, [Validators.required, Validators.min(0)]]
    });

  }

  ngOnInit(): void {
    this.initializeForms();
    this.loadInvoiceData();
    this.loadProducts();
    this.loadVendors();
  }

  private initializeForms(): void {
    this.invoiceForm = this.fb.group({
      invoiceNo: [{ value: '', disabled: true }, Validators.required],
      date: [{ value: '', disabled: true }, Validators.required],
      clientVendor: ['', Validators.required]
    });

    this.newInvoiceProductForm = this.fb.group({
      product: ['', Validators.required],
      quantity: [1, [Validators.required, Validators.min(1)]],
      price: [0, [Validators.required, Validators.min(0)]],
      tax: [0, [Validators.required, Validators.min(0)]]
    });
  }

  private loadInvoiceData(): void {
    // Assume this method fetches the invoice data based on some ID
    this.invoiceService.getInvoicesForACompany().subscribe(invoice => {
      this.invoice = invoice;
      this.invoiceForm.patchValue(invoice);
    });
  }

  private loadProducts(): void {
    this.productService.getProducts().subscribe(products => {
      this.products = products;
    });
  }

  private loadVendors(): void {
    this.clientVendorsService.getAllClientVendors().subscribe(vendors => {
      this.vendors = vendors;
    });
  }

  updateInvoice(): void {
    if (this.invoiceForm.valid) {
      this.invoiceService.updatePurchaseInvoice(0,this.invoiceForm.value).subscribe(() => {
        // Handle successful update
        this.router.navigate(['/purchaseInvoices/list']);
      }, error => {
        // Handle error
        console.error('Update failed', error);
      });
    }
  }

  addInvoiceProduct(): void {
    if (this.newInvoiceProductForm.valid) {
      this.invoiceService.addProductToPurchaseInvoice(0,this.newInvoiceProductForm.value).subscribe(() => {
        // Handle successful addition
        this.loadInvoiceData(); // Refresh the invoice data to show new product
        this.newInvoiceProductForm.reset(); // Reset the form
      }, error => {
        // Handle error
        console.error('Add product failed', error);
      });
    }
  }

  removeInvoiceProduct(productId: number): void {
    this.invoiceService.deletePurchaseInvoice(productId).subscribe(() => {
      // Handle successful removal
      this.loadInvoiceData(); // Refresh the invoice data to reflect removal
    }, error => {
      // Handle error
      console.error('Remove product failed', error);
    });
  }

}
