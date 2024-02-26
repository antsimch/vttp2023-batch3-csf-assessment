export interface News {
    id: string,
    postDate: number,
    title: string,
    description: string,
    image: string,
    tags: string[]
}

export interface TagCount {
    tag: string,
    count: number
}
