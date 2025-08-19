<script lang="ts">
    import type { Category } from '$lib/types/types.js';
    const { data, form } = $props();
    const { dish, username } = data;

    let mainCategories = $derived(data.categories.filter((cat: Category) => !cat.parentCategory));
    let getSubcategories = (parentCat: string) => {
        return data.categories.filter((cat: Category) => 
            cat.parentCategory && cat.parentCategory.category === parentCat
        );
    };

    
</script>

<header class="bg-white shadow">
    <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
        <h1 class="text-2xl font-semibold text-gray-900">Welcome {username}</h1>
        <div class="flex items-center space-x-4">
            <a href="/dashboard" 
                class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                Dashboard
            </a>
        </div>
    </div>
</header>
<main class="mt-10 flex justify-center">
    <!-- Forms Section -->
    <div class="w-full max-w-5xl">
        <!-- Add Dish Form -->
        <div class="bg-white shadow rounded-lg p-6">
            <h2 class="text-xl font-semibold mb-4">Update dish: {dish.name}</h2>
            <form method="POST" action="?/update" class="space-y-4" enctype="multipart/form-data">
                <div>
                    <input class="w-full px-3 py-2 border rounded-md" type="text"
                        name="name" placeholder="Dish name" />
                </div>
                <div>
                    <textarea class="w-full px-3 py-2 border rounded-md h-100"
                        name="description" placeholder="Description" rows="3" value="{dish.description}"></textarea>
                </div>
                <div>
                    <div class="w-full px-3 py-2 border rounded-md">
                        {#each mainCategories as category}
                        <p class="border-1 p-1 m-1">
                            <label for="{category.category}">{category.category}</label>
                            
                            {#if dish.categories.some((cat: Category) => cat.category == category.category)}
                            <input checked name="selected-categories" class="float-end" type="checkbox" id="{category.category}" value="{category.category}">
                            {:else}
                            <input name="selected-categories" class="float-end" type="checkbox" id="{category.category}" value="{category.category}">
                            {/if}
                        </p>
                            {#each getSubcategories(category.category) as subcategory}
                            <p class="border-1 p-1 m-1 ml-3">
                                <label for="{subcategory.category}">{subcategory.category}</label>
                                {#if dish.categories.some((cat: Category) => cat.category == subcategory.category)}
                                <input checked name="selected-categories" class="float-end" type="checkbox" id="{subcategory.category}" value="{subcategory.category}">
                                {:else}
                                <input name="selected-categories" class="float-end" type="checkbox" id="{subcategory.category}" value="{subcategory.category}">
                                {/if}
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
                <button class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700">
                    Update Dish
                </button>
            </form>
            {#if form?.success}
                <p class="mt-2 text-green-500">The dish was updated!</p>
            {/if}
            {#if form?.error}
                <p class="mt-2 text-red-500">{form.error}</p>
            {/if}
        </div>
    </div>
</main>