<script lang="ts">
    const { data, form } = $props();
    import { env } from '$env/dynamic/public';
    const FILE_URL = env.PUBLIC_FILE_URL
    const { username, role, categories } = data;
    import type { Category } from '$lib/types/types.js';

    let mainCategories = $derived(categories.filter((cat: Category) => !cat.parentCategory));
    let getSubcategories = (parentCat: string) => {
        return categories.filter((cat: Category) => 
            cat.parentCategory && cat.parentCategory.category === parentCat
        );
    };

    let dishesReversed = [...data.dishes].reverse();

</script>

<div class="min-h-screen bg-gray-50">
    <!-- Header -->
    <header class="bg-white shadow">
        <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
            <h1 class="text-2xl font-semibold text-gray-900">Welcome {username}</h1>
            <div class="flex items-center space-x-4">
                {#if role === 'ADMIN'}
                    <a href="/users" 
                        class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                        Users
                    </a>
                {:else}
                    <a href="/users" 
                        class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                        User Settings
                    </a>
                {/if}
                <form action="?/logout" method="POST" class="inline">
                    <button class="px-4 py-2 text-red-600 hover:text-red-800 font-medium cursor-pointer">
                        Logout
                    </button>
                </form>
            </div>
        </div>
    </header>

    <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <!-- Forms Section -->
        <div class="grid md:grid-cols-2 gap-6 mb-8">
            <!-- Add Dish Form -->
            <div class="bg-white shadow rounded-lg p-6">
                <h2 class="text-xl font-semibold mb-4">Add a Dish</h2>
                <form method="POST" action="?/add" class="space-y-4" enctype="multipart/form-data">
                    <div>
                        <input class="w-full px-3 py-2 border rounded-md" type="text"
                            name="name" placeholder="Dish name" required />
                    </div>
                    <div>
                        <textarea class="w-full px-3 py-2 border rounded-md"
                            name="description" placeholder="Description" rows="3" required></textarea>
                    </div>
                    <div>
                        <div class="w-full px-3 py-2 border rounded-md">
                            {#each mainCategories as category}
                            <p class="bg-gray-50 rounded p-2 m-1">
                                <label for="{category.category}">{category.category}</label>
                                <input name="selected-categories" class="float-end" type="checkbox" id="{category.category}" value="{category.category}">
                            </p>
                                {#each getSubcategories(category.category) as subcategory}
                                <p class="bg-gray-100 rounded p-2 m-1 ml-4">
                                    <label for="{subcategory.category}">↳ {subcategory.category}</label>
                                    <input name="selected-categories" class="float-end" type="checkbox" id="{subcategory.category}" value="{subcategory.category}">
                                </p>
                                {/each}
                            {/each}
                        </div>
                    </div>
                    <div>
                        <input class="w-full px-3 py-2 border rounded-md" 
                            type="file" accept="image/*"
                            name="image" />
                    </div>
                    <button class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 cursor-pointer">
                        Add Dish
                    </button>
                </form>
                {#if form?.dishError}
                    <p class="mt-2 text-red-500">{form.dishError}</p>
                {/if}
            </div>

            <!-- Categories Section -->
            <div class="bg-white shadow rounded-lg p-6">
                <h2 class="text-xl font-semibold mb-4">Categories</h2>
                <div class="mb-4">
                    <h3 class="font-medium mb-2">Categories and Subcategories:</h3>
                    <div class="space-y-2">
                        {#each mainCategories as category}
                            <!-- Main category -->
                            <div class="space-y-1">
                                <div class="flex justify-between items-center p-2 bg-gray-50 rounded">
                                    <span class="font-medium">{category.category}</span>
                                    <div>
                                        <form action="?/up" method="POST" class="inline">
                                            <input type="hidden" name="category" value={category.category}>
                                            <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Up</button>
                                        </form>
                                        <form action="?/down" method="POST" class="inline">
                                            <input type="hidden" name="category" value={category.category}>
                                            <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Down</button>
                                        </form>
                                        <form action="?/deleteCategory" method="POST" class="inline">
                                            <input type="hidden" name="category" value={category.category}>
                                            <button class="text-red-600 hover:text-red-800 ml-2 mr-2 cursor-pointer">Delete</button>
                                        </form>
                                    </div>
                                </div>
                                <!-- Subcategories -->
                                {#each getSubcategories(category.category) as subcategory}
                                    <div class="flex justify-between items-center p-2 bg-gray-100 rounded ml-4">
                                        <span class="text-sm">↳ {subcategory.category}</span>
                                        <div>
                                            <form action="?/up" method="POST" class="inline">
                                                <input type="hidden" name="category" value={subcategory.category}>
                                                <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Up</button>
                                            </form>
                                            <form action="?/down" method="POST" class="inline">
                                                <input type="hidden" name="category" value={subcategory.category}>
                                                <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Down</button>
                                            </form>
                                            <form action="?/deleteCategory" method="POST" class="inline">
                                                <input type="hidden" name="category" value={subcategory.category}>
                                                <button class="text-red-600 hover:text-red-800 ml-2 mr-2 cursor-pointer">Delete</button>
                                            </form>
                                        </div>
                                    </div>
                                {/each}
                            </div>
                        {/each}
                    </div>
                </div>

                <!-- Update Category Form -->
                <form method="POST" action="?/addCategory" class="space-y-4">
                    <div>
                        <input class="w-full px-3 py-2 border rounded-md" type="text"
                            name="category" placeholder="Category name" required />
                    </div>
                    <div>
                        <select class="w-full px-3 py-2 border rounded-md"
                            name="parentCategory">
                            <option value="">Select parent category (optional)</option>
                            {#each mainCategories as category}
                                <option value={category.category}>{category.category}</option>
                            {/each}
                        </select>
                    </div>
                    <button class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 cursor-pointer">
                        Add Category
                    </button>
                </form>
                {#if form?.categoryError}
                    <p class="mt-2 text-red-500">{form.categoryError}</p>
                {/if}
            </div>
        </div>

        <!-- Dishes Grid -->
        <div class="grid md:grid-cols-2 lg:grid-cols-3 gap-6">
            {#each dishesReversed as dish}
                <div class="bg-white shadow rounded-lg">
                    <a href="/dishes/{dish.id}">
                    <img 
                        class="w-full h-48 object-cover cursor-pointer" 
                        src="{`${FILE_URL}/${dish.imageName}`}"
                        alt={dish.name}
                        loading="lazy"
                    />
                    </a>
                    <div class="p-6">
                        <div class="flex justify-between items-start">
                            <h3 class="text-xl font-semibold max-w-0.5 hover:text-emerald-500 hover:underline"><a href="/dishes/{dish.id}">{dish.name}</a></h3>
                            <form action="?/deleteDish" method="POST" class="inline">
                                <input type="hidden" name="id" value={dish.id}>
                                <input type="hidden" name="imageName" value={dish.imageName}>
                                <button class="text-red-600 rounded-md hover:text-red-700 m-2 cursor-pointer">
                                    Delete
                                </button>
                                <a href="/update/{dish.id}" class="text-blue-600 rounded-md hover:text-blue-700 m-2">
                                    Update
                                </a>
                            </form>
                        </div>
                        <p class="mt-4 text-sm text-gray-500">Categories:</p>
                        <div class="flex overflow-auto">
                            {#each dish.categories as category}
                            <p class="m-1">{category.category}</p>
                            {/each}
                        </div>
                    </div>
                </div>
            {/each}
        </div>
    </main>
</div>