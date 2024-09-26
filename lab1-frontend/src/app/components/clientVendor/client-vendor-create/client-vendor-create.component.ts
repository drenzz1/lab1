import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {ClientVendorType} from "../../../enums/client-vendor-type";
import {ClientVendorService} from "../../../services/client-vendor.service";

@Component({
  selector: 'app-client-vendor-create',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './client-vendor-create.component.html',
  styleUrl: './client-vendor-create.component.css'
})
export class ClientVendorCreateComponent implements OnInit{

  clientVendorForm: FormGroup;
  clientVendorTypes = Object.values(ClientVendorType);

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private clientVendorService:ClientVendorService
  ) {
    this.clientVendorForm = this.fb.group({
      clientVendorName: ['', [Validators.required, Validators.minLength(3)]],
      clientVendorType: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      website: ['', [Validators.required, Validators.pattern(/https?:\/\/[^\s$.?#].[^\s]*$/i)]],
      address: this.fb.group({
        addressLine1: ['', Validators.required],
        addressLine2: [''],
        city: ['', Validators.required],
        zipCode: ['', [Validators.required, Validators.pattern(/^\d{5}(-\d{4})?$/)]],
        state: ['', Validators.required],
        country: ['', Validators.required]
      })
    });
  }

  ngOnInit(): void {
    this.clientVendorForm = this.fb.group({
      clientVendorName: ['', [Validators.required, Validators.minLength(3)]],
      clientVendorType: ['', Validators.required],
      phone: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      website: ['', [Validators.required, Validators.pattern(/https?:\/\/[^\s$.?#].[^\s]*$/i)]],
        addressLine1: ['', Validators.required],
        addressLine2: [''],
        city: ['', Validators.required],
        zipCode: ['', [Validators.required, Validators.pattern(/^\d{5}(-\d{4})?$/)]],
        state: ['', Validators.required],
        country: ['', Validators.required]

    });
  }

  onSubmit(): void {
    if (this.clientVendorForm.valid) {
      // Handle form submission
      console.log('Form Submitted', this.clientVendorForm.value);

      this.clientVendorService.createClientVendor(this.clientVendorForm.value).subscribe({
        next: () => this.router.navigate(['/layout/client-vendor']),
        error: (err) => console.error('Error creating company', err)
      })
      // Navigate to a different page or show a success message
      ;
    }
  }

  onReset(): void {
    this.clientVendorForm.reset();
  }

  getErrors(controlName: string): string[] {
    const control = this.clientVendorForm.get(controlName);
    if (control && control.errors) {
      return Object.keys(control.errors).map(key => {
        switch (key) {
          case 'required':
            return 'This field is required';
          case 'minlength':
            return `Minimum length is ${control.errors}['minlength'].requiredLength}`;
          case 'pattern':
            return 'Invalid format';
          default:
            return 'Invalid field';
        }
      });
    }
    return [];
  }


}
