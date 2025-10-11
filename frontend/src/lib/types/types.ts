export interface Category {
    category: string,
    parentCategory: Category | null
    childCategories?: Category[]
    position?: number
}

export interface Dish {
    id: number,
    name: string,
    description: string,
    categories: Array<Category>,
    imageName: string
}