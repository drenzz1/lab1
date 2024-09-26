import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {RouterLink} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {TaskService} from "../../services/task.service";

@Component({
  selector: 'app-task-create',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf,
    DatePipe,
    RouterLink
  ],
  templateUrl: './task-create.component.html',
  styleUrl: './task-create.component.css'
})

export class TaskComponent implements OnInit {
  taskForm: FormGroup;
  tasks: any[] = []; // Replace with your actual task model
  projects: any[] = []; // Replace with your actual project model
  employees: any[] = []; // Replace with your actual employee model

  constructor(private fb: FormBuilder,private projectService:ProjectService,private userService:UserService,private taskService:TaskService) {
    this.taskForm = this.fb.group({
      project: ['', Validators.required],
      assignedEmployee: ['', Validators.required],
      taskSubject: ['', Validators.required],
      taskDetail: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadProjects();
    this.loadEmployees();
    this.loadTasks();
  }

  loadProjects() {
    this.projectService.getAllProjects().subscribe(data=>{
      this.projects=data;
    })
  }

  loadEmployees() {
    this.userService.listAllEmployees("Employee").subscribe(data=>{
      this.employees=data;
    })
  }

  loadTasks() {
    this.taskService.getAllTasks().subscribe(data=>{
      this.tasks=data;
    })
  }

  onSubmit() {
    if (this.taskForm.valid) {
      const taskData = this.taskForm.value;
      this.taskService.createTask(taskData).subscribe({
        next: () => {
          location.reload();
        },
        error: (err) => console.error('Error creating user', err)
      });
    }
  }

  getFieldErrors(fieldName: string): string[] {
    const control = this.taskForm.get(fieldName);
    if (control && control.errors) {
      return Object.keys(control.errors).map(errorKey => {
        switch (errorKey) {
          case 'required':
            return `${fieldName} is required`;
          default:
            return 'Invalid input';
        }
      });
    }
    return [];
  }

  // Additional methods for updating and deleting tasks can be added here
}
