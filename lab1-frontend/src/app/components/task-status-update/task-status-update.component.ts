import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {ActivatedRoute, Router} from "@angular/router";
import {TaskService} from "../../services/task.service";
import {Status, StatusArray} from "../../enums/status";

@Component({
  selector: 'app-task-status-update',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf
  ],
  templateUrl: './task-status-update.component.html',
  styleUrl: './task-status-update.component.css'
})
export class TaskStatusUpdateComponent  implements OnInit{
  taskForm!: FormGroup;
  task: any = {}; // Assuming this will be populated from an API or service
  statuses = StatusArray; // Use the StatusArray for dropdown options

  taskId: number = 0;

  constructor(private fb: FormBuilder,private route: ActivatedRoute,private taskService:TaskService,private router :Router) {}

  ngOnInit(): void {
    this.taskId = this.route.snapshot.params['id'];  // Get task id from route params

    // Initialize form
    this.taskForm = this.fb.group({
      project: ['', Validators.required],
      assignedEmployee: ['', Validators.required],
      taskSubject: ['', Validators.required],
      taskDetail: ['', Validators.required],
      taskStatus : ['', Validators.required]

    });


    // Fetch task data by id and populate form
    this.taskService.getTaskById(this.taskId).subscribe(task => {
      this.taskForm.patchValue({
        project: task.project.projectCode,
        assignedEmployee: task.assignedEmployee.firstName,
        taskSubject: task.taskSubject,
        taskDetail: task.taskDetail,
        taskStatus:task.taskStatus,
        assignedDate:task.assignedDate

      });
    });

    // Fetch projects and employees
  }

  onSubmit() {


    const taskStatus = this.taskForm.get('taskStatus')?.value;
    this.taskService.changeStatus(this.taskId,taskStatus).subscribe(() => {
      this.router.navigate(['/layout/pending-tasks']);}
    )
  }

}
