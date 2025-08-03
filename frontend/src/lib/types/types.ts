export interface Category {
    category: string,
    parentCategory: Category | null
}

export interface Dish {
    id: number,
    name: string,
    description: string,
    category: Category
    imageName: string
}