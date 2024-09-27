import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators,} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {User} from "../../../common/user";
import {ProjectService} from "../../../services/project.service";


@Component({
  selector: 'app-user-create',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgForOf,
    NgIf,
    RouterLink
  ],
  templateUrl: './user-create.component.html',
  styleUrl: './user-create.component.css'
})
export class UserCreateComponent implements OnInit{
  userForm: FormGroup;
  canIDelete: { [key: number]: boolean } = {}; // Store delete permissions for each user


  users: any[] = []; // Replace with your actual user model
  roles: any[] = []; // Replace with your actual role model

  constructor(private fb: FormBuilder , private userService : UserService, private router :Router,private projectService:ProjectService) {
    this.userForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      userName: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      passWord: ['', Validators.required],
      confirmPassWord: ['', Validators.required],
      gender: ['', Validators.required],
      role: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    this.loadRoles();
    this.loadUsers();
  }

  loadRoles() {
    this.userService.listAllRoles().subscribe(data =>{
      this.roles=data;
    })
  }

  loadUsers() {
    this.userService.getAllUsers().subscribe(data =>{
      this.users=data;
      this.checkUsersProjectsOrTasks(); // Check projects/tasks after loading users

    })
  }

  onSubmit() {
    if (this.userForm.valid) {
      const createdUser = new User(0,this.userForm.get('firstName')?.value,this.userForm.get('lastName')?.value,this.userForm.get('userName')?.value,this.userForm.get('passWord')?.value,this.userForm.get('phone')?.value,this.userForm.get('role')?.value,this.userForm.get('gender')?.value);

      this.userService.createUser(createdUser).subscribe({
        next: () => {
          location.reload(); // Refresh the page after successful submission
        },
        error: (err) => console.error('Error creating user', err)
      });
    }





  }

  getFieldErrors(fieldName: string): string[] {
    const control = this.userForm.get(fieldName);
    if (control && control.errors) {
      return Object.keys(control.errors).map(errorKey => {
        switch (errorKey) {
          case 'required':
            return `${fieldName} is required`;
          case 'email':
            return 'Invalid email format';
          default:
            return 'Invalid input';
        }
      });
    }
    return [];
  }

  onUpdate(user: number) {

    this.router.navigate([`/layout/user-edit/${user}`]); // Navigating to 'user-edit' route with user ID

  }

  checkUsersProjectsOrTasks() {
    this.users.forEach(user => {
      this.projectService.hasProjectsOrTasks(user.id).subscribe(
        (result: boolean) => {
          this.canIDelete[user.id] = !result; // Set canIDelete to true if no tasks/projects exist
        },
        (error) => {
          console.error('Error fetching data:', error);
          this.canIDelete[user.id] = true; // Default to true if there’s an error
        }
      );
    });
  }

  onDelete(userName: number) {
    this.userService.deleteUser(userName).subscribe({
      next: () => {
        location.reload(); // Refresh the page after successful submission
      },
      error: (err) => console.error('Error creating user', err)
    });
  }

}
