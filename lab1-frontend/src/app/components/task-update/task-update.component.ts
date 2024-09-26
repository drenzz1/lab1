import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {TaskService} from "../../services/task.service";
import {ActivatedRoute, Router} from "@angular/router";
import {ProjectService} from "../../services/project.service";
import {UserService} from "../../services/user.service";
import {ProjectDto} from "../../common/project-dto";
import {User} from "../../common/user";
import {TaskDto} from "../../common/task-dto";
import {Role} from "../../common/role";
import {Gender} from "../../enums/gender";
import {Status} from "../../enums/status";

@Component({
  selector: 'app-task-update',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf
  ],
  templateUrl: './task-update.component.html',
  styleUrl: './task-update.component.css'
})
export class TaskUpdateComponent implements OnInit{
  taskEdit:TaskDto = new TaskDto(0, new ProjectDto(0, '', '', new User(0, '', '', '', '', '', new Role(0, ''), Gender.MALE), new Date(), new Date(), '', Status.OPEN, 0, 0),new User(0, '', '', '', '',  '', new Role(0, ''), Gender.MALE),'','',Status.OPEN,new Date());
  projectText!: FormControl;


  taskForm!: FormGroup;
  projects:ProjectDto[] = [];   // Should be populated with actual projects
  employees:User[] = [];  // Should be populated with actual employees
  taskId: number = 0;
   assignedEmployee!: FormControl

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,   // Your service to handle the update operation
    private router: Router,
    private route: ActivatedRoute,
    private projectService:ProjectService,
    private userServicce:UserService
  ) { }

  ngOnInit(): void {
    this.taskId = this.route.snapshot.params['id'];  // Get task id from route params

    // Initialize form
    this.taskForm = this.fb.group({
      project: ['', Validators.required],
      assignedEmployee: ['', Validators.required],
      taskSubject: ['', Validators.required],
      taskDetail: ['', Validators.required]
    });


    // Fetch task data by id and populate form
    this.taskService.getTaskById(this.taskId).subscribe(task => {
      this.projectText = new FormControl({ value: task.project.projectName, disabled: true }); // Set readonly
      this.assignedEmployee = new FormControl({ value: task.assignedEmployee.firstName + '  ' + task.assignedEmployee.lastName , disabled: true }); // Set readonly

      this.taskForm.patchValue({
        project: task.project,
        assignedEmployee: task.assignedEmployee,
        taskSubject: task.taskSubject,
        taskDetail: task.taskDetail,
        taskStatus:task.taskStatus,
        assignedDate:task.assignedDate,


      });
    });

    // Fetch projects and employees
    this.loadProjects();
    this.loadEmployees();
    this.initForm();


  }

  initForm() {
    this.taskForm = this.fb.group({
      project: ['', Validators.required],
      assignedEmployee: ['', Validators.required], // Assuming project code is readonly
      taskSubject: ['', Validators.required],
      taskDetail: ['', Validators.required],
    });
  }

  loadProjects() {
    // Call the API or service to fetch the project list
    this.projectService.getAllProjects().subscribe((data) => {
      this.projects = data;
    });
  }

  loadEmployees() {
    // Call the API or service to fetch the employee list
    this.userServicce.listAllEmployees("Employee").subscribe((data) => {
      this.employees = data;
    });
  }

  onSubmit() {
    if (this.taskForm.valid) {
      const updatedTask = this.taskForm.value;
      console.log(updatedTask)
      this.taskService.updateTask(this.taskId, updatedTask).subscribe(() => {
        this.router.navigate(['/layout/task-create']);  // Navigate back to tasks list or somewhere else
      });
    }
  }



}
