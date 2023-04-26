import { Component, Input } from '@angular/core';
import IReview from 'src/app/models/review.model';
import IUser from 'src/app/models/user.model';
import { ReviewService } from 'src/app/services/review.service';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-reviews-list',
  templateUrl: './reviews-list.component.html',
  styleUrls: ['./reviews-list.component.css']
})
export class ReviewsListComponent {
  movieId = this.route.snapshot.params['id'];
  reviews: IReview[] = []
  noReviews = true
  crtUserId = null

  constructor(public reviewService: ReviewService,
              public authService: AuthService,
              public route: ActivatedRoute,
              public router: Router) {}

  ngOnInit() {
    this.loadReviews()
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;    
  }

  loadReviews() {
    this.reviewService.getReviews(this.movieId).subscribe({
      next: result => {
        const crtUser = this.authService.getDecodedAccessToken()
        if (crtUser != null)
          this.crtUserId = crtUser.ID
        this.reviews = result
        this.reviews.forEach((element: any) => {
          element.userId = element.reviewBy.id
          element.reviewBy = element.reviewBy.name
        })
        this.noReviews = false
      },
      error: error => {
        console.log(error)
        return
      }
    })
  }

  deleteReview(id: string) {
    this.reviewService.deleteReview(id).subscribe({
      next: () => {
        this.router.navigate([this.router.url])
      },
      error: error => {
        if(error.status === 200) {
          this.router.navigate([this.router.url])
        }
        else {
          console.log(error)
          return
        }
      }
    })
  }

}
