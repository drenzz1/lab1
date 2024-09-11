import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientVendorListComponent } from './client-vendor-list.component';

describe('ClientVendorListComponent', () => {
  let component: ClientVendorListComponent;
  let fixture: ComponentFixture<ClientVendorListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientVendorListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientVendorListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
