export interface Category {
    name: string,
    parentCategory: Category | null
    childCategories?: Category[]
    position?: number
}

export interface CreateCategory {
    name: string,
    parentCategory?: string
}

export interface Dish {
    id: number,
    name: string,
    description: string,
    categories: Array<Category>,
    imageName: string
}