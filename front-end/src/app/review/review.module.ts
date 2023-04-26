import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReviewsListComponent } from './reviews-list/reviews-list.component';
import { NewReviewComponent } from './new-review/new-review.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatTooltipModule} from '@angular/material/tooltip';



@NgModule({
  declarations: [
    ReviewsListComponent,
    NewReviewComponent
  ],
  imports: [
    CommonModule,
    SharedModule,
    ReactiveFormsModule,
    FormsModule,
    MatGridListModule,
    MatCardModule,
    MatButtonModule,
    MatIconModule,
    MatSnackBarModule,
    MatTooltipModule
  ],
  exports: [
    ReviewsListComponent,
    NewReviewComponent
  ]
})
export class ReviewModule { }
