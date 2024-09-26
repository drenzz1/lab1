import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DatePipe, NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {UserService} from "../../services/user.service";
import {ProjectService} from "../../services/project.service";

@Component({
  selector: 'app-project-create',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgForOf,
    RouterLink,
    DatePipe
  ],
  templateUrl: './project-create.component.html',
  styleUrl: './project-create.component.css'
})
export class ProjectCreateComponent implements OnInit{
  projectForm: FormGroup;
  projects: any[] = []; // Replace with your actual project model
  managers: any[] = []; // Replace with your actual manager model

  constructor(private fb: FormBuilder,private userService : UserService,private projectService:ProjectService,private router :Router) {
    this.projectForm = this.fb.group({
      projectName: ['', Validators.required],
      projectCode: ['', Validators.required],
      assignedManager: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      projectDetail: ['', Validators.required],
    });
  }

  ngOnInit(): void {


    this.loadManagers();
    this.loadProjects();

  }

  loadManagers() {

    this.userService.listAllManagers("Manager").subscribe(
      data=>{
        console.log(data);
        this.managers=data;
      }
    );
  }


  loadProjects() {

    this.projectService.getAllProjects().subscribe(data=>{
      this.projects=data;
      console.log("hello")

    })
  }

  onSubmit() {
    if (this.projectForm.valid) {
      const projectData = this.projectForm.value;
      console.log(projectData)

      this.projectService.createProject(projectData).subscribe({
        next: () => {
          location.reload();
        },
        error: (err) => console.error('Error creating user', err)
      });



    }
  }

  getFieldErrors(fieldName: string): string[] {
    const control = this.projectForm.get(fieldName);
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


  onDelete(id:number) {
    this.projectService.deleteProject(id).subscribe({
      next: () => {
        location.reload(); // Refresh the page after successful submission
      },
      error: (err) => console.error('Error creating user', err)
    });
  }

  update(id:number) {
    this.router.navigate([`/layout/project-edit/${id}`]); // Navigating to 'user-edit' route with user ID
  }

  complete(id:number) {
    console.log("HEllo")
    console.log(this.projects)

    this.projectService.completeProject(id).subscribe({
      next: () => {

      },
      error: (err) => console.error('Error creating user', err)
    });
  }
}
