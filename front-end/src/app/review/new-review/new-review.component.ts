import { Component, Input, Output, EventEmitter } from '@angular/core';
import { ReviewService } from 'src/app/services/review.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-new-review',
  templateUrl: './new-review.component.html',
  styleUrls: ['./new-review.component.css']
})
export class NewReviewComponent {
  @Input('rating') rating: number = 3;
  @Input('starCount') starCount: number = 5;
  @Input('color') color: string = 'accent';
  movieId = this.route.snapshot.params['id'];

  ratingArr: number[] = [];

  review = {
    text: '',
    rating: 0
  }

  constructor(private reviewService: ReviewService,
    public route: ActivatedRoute,
    public router: Router) { }


  ngOnInit() {
    for (let index = 0; index < this.starCount; index++) {
      this.ratingArr.push(index);
    }
  }
  onClick(rating: number) {
    this.rating = rating
    return false;
  }

  showIcon(index: number) {
    if (this.rating >= index + 1) {
      return 'star';
    } else {
      return 'star_border';
    }
  }

  inSubmission = false

  showAlert = false
  alertMsg = 'Sending review...'
  alertColour = 'blue'

  sendReview() {
    this.showAlert = true
    this.alertMsg = 'Sending review...'
    this.alertColour = 'blue'
    this.inSubmission = true
    const user = localStorage.getItem('user')
    if (!user) {
      this.alertMsg = 'You must have an account to leave a review!'
      this.alertColour = 'red'
      this.inSubmission = false
      return
    }
    this.reviewService.sendReview(this.review.text, this.rating.toString(), this.movieId)
      .subscribe({
        next: () => {
          this.alertMsg = 'Review successfully sent.'
          this.alertColour = 'green'
          setTimeout(() => this.router.navigate([this.router.url]), 1000)
        },
        error: error => {
          if (error.status === 200) {
            this.alertMsg = 'Review successfully sent.'
            this.alertColour = 'green'
            setTimeout(() => this.router.navigate([this.router.url]), 1000)
          }
          else {
            console.log(error)
            this.alertMsg = error.error
            this.alertColour = 'red'
            this.inSubmission = false
            return
          }
        }
      })
  }
}
