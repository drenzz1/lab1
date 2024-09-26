import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientVendorUpdateComponent } from './client-vendor-update.component';

describe('ClientVendorUpdateComponent', () => {
  let component: ClientVendorUpdateComponent;
  let fixture: ComponentFixture<ClientVendorUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientVendorUpdateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientVendorUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
