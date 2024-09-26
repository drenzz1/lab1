import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { NgForOf, NgIf } from "@angular/common";
import { User } from "../../common/user";
import { UserService } from "../../services/user.service";
import { ProjectDto } from "../../common/project-dto";
import { Role } from "../../common/role";
import { Gender } from "../../enums/gender";
import { Status } from "../../enums/status";
import {ActivatedRoute, Router} from "@angular/router";
import { ProjectService } from "../../services/project.service";

@Component({
  selector: 'app-project-update',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    ReactiveFormsModule
  ],
  templateUrl: './project-update.component.html',
  styleUrls: ['./project-update.component.css']
})
export class ProjectUpdateComponent implements OnInit {

  projectEdit: ProjectDto = new ProjectDto(0, '', '', new User(0, '', '', '', '', '', new Role(0, ''), Gender.MALE), new Date(), new Date(), '', Status.OPEN, 0, 0);
  projectId = 0;
  projectForm!: FormGroup;
  managers: User[] = [];

  constructor(private fb: FormBuilder, private projectService: ProjectService, private userService: UserService, private route: ActivatedRoute,private router:Router) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.projectId = +params["id"];
      this.getProject(this.projectId);
      this.userService.listAllManagers("Manager").subscribe(data => {
        this.managers = data;
      });
    });

    this.initForm();
  }

  initForm() {
    this.projectForm = this.fb.group({
      projectName: ['', Validators.required],
      projectCode: [{ value: '', disabled: true }], // Assuming project code is readonly
      assignedManager: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required],
      projectDetail: ['']
    });
  }

  getFormErrors(controlName: string): string[] {
    const control = this.projectForm.get(controlName);
    if (control && control.errors && control.touched) {
      return Object.keys(control.errors).map(err => {
        switch (err) {
          case 'required':
            return 'This field is required';
          default:
            return '';
        }
      });
    }
    return [];
  }

  onSubmit(): void {
    if (this.projectForm.valid) {
      this.projectEdit = this.projectForm.getRawValue(); // Use getRawValue to get values from disabled fields like projectCode

      console.log(this.projectEdit)

      this.projectService.updateProject(this.projectId, this.projectEdit).subscribe(response => {
        this.router.navigate(['/layout/project-create']); // Redirect to users list
        console.log('Project updated successfully', response);

        // Handle success - possibly navigate or show a success message
      }, error => {
        console.error('Error updating project', error);
        // Handle error
      });
    }
  }

  getProject(projectId: number) {
    this.projectService.getProjectById(projectId).subscribe((project: ProjectDto) => {
      this.projectEdit = project;
      this.populateForm();
    });
  }

  private populateForm() {
    this.projectForm.patchValue({
      projectName: this.projectEdit.projectName,
      projectCode: this.projectEdit.projectCode,
      assignedManager: this.projectEdit.assignedManager,
      startDate: this.projectEdit.startDate,
      endDate: this.projectEdit.endDate,
      projectDetail: this.projectEdit.projectDetail,
      projectStatus: this.projectEdit.projectStatus,          // Assuming Status is an enum
      completeTaskCounts: this.projectEdit.completeTaskCounts,
      unfinishedTaskCounts: this.projectEdit.unfinishedTaskCounts


    });
  }
}
