import { Component, OnInit } from '@angular/core';
import { NgForOf, NgIf } from "@angular/common";
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { UserService } from "../../../services/user.service";
import { ActivatedRoute, Router } from "@angular/router";
import { User } from "../../../common/user";
import { Role } from "../../../common/role";
import { Gender } from "../../../enums/gender";

@Component({
  selector: 'app-user-update',
  standalone: true,
  imports: [
    NgForOf,
    NgIf,
    ReactiveFormsModule
  ],
  templateUrl: './user-update.component.html',
  styleUrls: ['./user-update.component.css']
})
export class UserUpdateComponent implements OnInit {

  userEdit: User = new User(0, '', '', '', '',  '', new Role(0, ''), Gender.MALE);
  userid = 0;
  userForm: FormGroup;
  roles: Role[] = [];

  constructor(private fb: FormBuilder, private userService: UserService, private router: Router, private route: ActivatedRoute) {
    this.userForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      userName: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      passWord: [''],  // Remove Validators.required
      gender: ['', Validators.required],
      role: ['', Validators.required],
    });
  }


  ngOnInit() {
    this.route.params.subscribe(params => {
      this.userid = +params["id"];
      this.getUser(this.userid);
      this.getRoles();
    });
  }

  private getUser(userId: number) {
    this.userService.getUserById(userId).subscribe((user: User) => {
      this.userEdit = user;
      this.populateForm();
    });
  }

  private populateForm() {
    this.userForm.patchValue({
      firstName: this.userEdit.firstName,
      lastName: this.userEdit.lastName,
      userName: this.userEdit.userName,
      passWord: '',  // Keep this empty to avoid showing the current password
      phone: this.userEdit.phone,
      gender: this.userEdit.gender,
      role: this.userEdit.role
    });
  }

  private getRoles() {
    this.userService.listAllRoles().subscribe(roles => {
      this.roles = roles;
    });
  }

  onSubmit() {
    console.log(this.userForm.value);

    const updatedUser: User = {
      ...this.userEdit,
      ...this.userForm.value,
      passWord: this.userForm.value.passWord && this.userForm.value.passWord.trim() ? this.userForm.value.passWord : this.userEdit.passWord
    };


      this.userService.updateUser(this.userid, updatedUser).subscribe(() => {
        this.router.navigate(['/layout/user-create']); // Redirect to users list
      }, error => {
        console.error('Error updating user', error);
        // Handle error (e.g., show a notification)
      });

  }



  getFieldErrors(fieldName: string) {
    const field = this.userForm.get(fieldName);
    return field?.errors ? Object.keys(field.errors).map(key => {
      switch (key) {
        case 'required':
          return `${fieldName} is required`;
        case 'email':
          return 'Invalid email format';
        default:
          return '';
      }
    }) : [];
  }
}
