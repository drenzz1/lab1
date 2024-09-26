import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TaskStatusUpdateComponent } from './task-status-update.component';

describe('TaskStatusUpdateComponent', () => {
  let component: TaskStatusUpdateComponent;
  let fixture: ComponentFixture<TaskStatusUpdateComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TaskStatusUpdateComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TaskStatusUpdateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
