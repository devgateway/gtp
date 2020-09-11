import PluviometricPostDTO from "./PluviometricPostDTO"

export default class PluviometricPostMapDTO {
  posts: Array<PluviometricPostDTO>

  constructor(posts: Array<PluviometricPostDTO>) {
    this.posts = posts
  }
}
