import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ClientVendorCreateComponent } from './client-vendor-create.component';

describe('ClientVendorCreateComponent', () => {
  let component: ClientVendorCreateComponent;
  let fixture: ComponentFixture<ClientVendorCreateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ClientVendorCreateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ClientVendorCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
