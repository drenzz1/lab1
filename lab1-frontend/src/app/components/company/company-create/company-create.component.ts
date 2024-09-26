import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {CompanyService} from "../../../services/company.service";
import {Router, RouterLink} from "@angular/router";
import {NgIf} from "@angular/common";
import {CompanyDto} from "../../../common/company";
import {CompanyStatus} from "../../../enums/company-status";

@Component({
  selector: 'app-company-create',
  standalone: true,
  imports: [
    NgIf,
    ReactiveFormsModule,
    RouterLink
  ],
  templateUrl: './company-create.component.html',
  styleUrl: './company-create.component.css'
})
export class CompanyCreateComponent implements OnInit{
  companyForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private companyService: CompanyService,
    private router: Router
  ) {
    this.companyForm = this.fb.group({
      title: ['', Validators.required],
      phone: ['', Validators.required],
      website: ['', Validators.required],
      addressLine1: ['', Validators.required],
      addressLine2: [''],
      city: ['', Validators.required],
      zipCode: ['', Validators.required],
      state: ['', Validators.required],
      country: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.companyForm = this.fb.group({
      title: ['', Validators.required],
      phone: ['', Validators.required],
      website: ['', Validators.required],
      addressLine1: ['', Validators.required],
      addressLine2: [''],
      city: ['', Validators.required],
      zipCode: ['', Validators.required],
      state: ['', Validators.required],
      country: ['', Validators.required]
    });
  }

  get title() { return this.companyForm.get('title'); }
  get phone() { return this.companyForm.get('phone'); }
  get website() { return this.companyForm.get('website'); }
  get addressLine1() { return this.companyForm.get('addressLine1'); }
  get addressLine2() { return this.companyForm.get('addressLine2'); }
  get city() { return this.companyForm.get('city'); }
  get zipCode() { return this.companyForm.get('zipCode'); }
  get state() { return this.companyForm.get('state'); }
  get country() { return this.companyForm.get('country'); }

  onSubmit(): void {
    if (this.companyForm.valid) {
      const companyDto = new CompanyDto(
        0, // ID should be set on the server side
        this.companyForm.get('title')?.value,
        this.companyForm.get('phone')?.value,
        this.companyForm.get('website')?.value,
          this.companyForm.get('addressLine1')?.value,
          this.companyForm.get('addressLine2')?.value,
          this.companyForm.get('city')?.value,
          this.companyForm.get('state')?.value,
          this.companyForm.get('country')?.value,
          this.companyForm.get('zipCode')?.value
        ,
        CompanyStatus.ACTIVE
      );
      this.companyService.createNewCompany(companyDto).subscribe({
        next: () => this.router.navigate(['/layout/company']),
        error: (err) => console.error('Error creating company', err)
      });
    }
  }

  onReset(): void {
    this.companyForm.reset();
  }
}
