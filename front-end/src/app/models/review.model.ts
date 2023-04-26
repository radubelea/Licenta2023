export default interface IReview {
    id: string;
    rating: number;
    text: string;
    reviewBy: string;
    userId: number;
    movieId: number;
}