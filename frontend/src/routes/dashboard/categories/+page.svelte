<script lang="ts">
  const { data, form } = $props();
  const { username, role, categories } = $derived(data);

  import type { Category } from '$lib/types/types.js';

  let mainCategories = $derived(categories.filter((cat: Category) => !cat.parentCategory));
  let getSubcategories = (parentCat: string) => {
    return categories.filter((cat: Category) =>
      cat.parentCategory && cat.parentCategory.name === parentCat
    );
  };
</script>

<svelte:head>
    <title>Koken met Lisa | Dashboard</title>
</svelte:head>

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
                <a href="/dashboard/dishes" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Dishes
                </a>
                <a href="/dashboard/inspirationdishes" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Inspiration Dishes
                </a>
                <form action="?/logout" method="POST" class="inline">
                    <button class="px-4 py-2 text-red-600 hover:text-red-800 font-medium cursor-pointer">
                        Logout
                    </button>
                </form>
            </div>
        </div>
    </header>

    <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
        <!-- Categories Section -->
        <div class="bg-white shadow rounded-lg p-6">
            {#if form?.categoryError}
                <p class="mt-2 text-red-500 bg-gray-100 border-1 border-gray-200 rounded p-2 mb-5">{form.categoryError}</p>
            {/if}
            <h2 class="text-xl font-semibold mb-4">Categories</h2>
            <div class="mb-4">
                <h3 class="font-medium mb-2">Categories and Subcategories:</h3>
                <div class="space-y-2">
                    {#each mainCategories as category}
                        <!-- Main category -->
                        <div class="space-y-1">
                            <div class="flex justify-between items-center p-2 bg-gray-50 rounded">
                                <span class="font-medium">{category.name}</span>
                                <div>
                                    <form action="?/up" method="POST" class="inline">
                                        <input type="hidden" name="category" value={category.name}>
                                        <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Up</button>
                                    </form>
                                    <form action="?/down" method="POST" class="inline">
                                        <input type="hidden" name="category" value={category.name}>
                                        <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Down</button>
                                    </form>
                                    <form action="?/deleteCategory" method="POST" class="inline">
                                        <input type="hidden" name="category" value={category.name}>
                                        <button class="text-red-600 hover:text-red-800 ml-2 mr-2 cursor-pointer">Delete</button>
                                    </form>
                                </div>
                            </div>
                            <!-- Subcategories -->
                            {#each getSubcategories(category.name) as subcategory}
                                <div class="flex justify-between items-center p-2 bg-gray-100 rounded ml-4">
                                    <span class="text-sm">↳ {subcategory.name}</span>
                                    <div>
                                        <form action="?/up" method="POST" class="inline">
                                            <input type="hidden" name="category" value={subcategory.name}>
                                            <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Up</button>
                                        </form>
                                        <form action="?/down" method="POST" class="inline">
                                            <input type="hidden" name="category" value={subcategory.name}>
                                            <button class="text-blue-600 hover:text-blue-800 ml-2 mr-2 cursor-pointer">Down</button>
                                        </form>
                                        <form action="?/deleteCategory" method="POST" class="inline">
                                            <input type="hidden" name="category" value={subcategory.name}>
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
                            <option value={category.name}>{category.name}</option>
                        {/each}
                    </select>
                </div>
                <button class="w-full bg-blue-600 text-white py-2 px-4 rounded-md hover:bg-blue-700 cursor-pointer">
                    Add Category
                </button>
            </form>
        </div>
    </main>
</div>