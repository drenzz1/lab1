import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators,} from "@angular/forms";
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink} from "@angular/router";
import {UserService} from "../../../services/user.service";
import {User} from "../../../common/user";


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

  users: any[] = []; // Replace with your actual user model
  roles: any[] = []; // Replace with your actual role model

  constructor(private fb: FormBuilder , private userService : UserService, private router :Router) {
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

  onUpdate(user: any) {
    this.router.navigate([`/layout/user-edit/${user.id}`]); // Navigating to 'user-edit' route with user ID
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
