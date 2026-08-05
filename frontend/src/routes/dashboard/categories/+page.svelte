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
      <div class="grid gap-6 lg:grid-cols-[360px_minmax(0,1fr)]">
        <section class="bg-white shadow rounded-lg p-6 space-y-6">
          <div>
            <h2 class="text-xl font-semibold mb-3">New Category</h2>
            {#if form?.categoryError}
              <p class="text-red-600 bg-red-50 border border-red-200 rounded p-3 mb-4">{form.categoryError}</p>
            {/if}
            <form method="POST" action="?/addCategory" class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700" for="category-name">Category name</label>
                <input id="category-name" name="category" required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700" for="parent-category">Parent category</label>
                <select id="parent-category" name="parentCategory"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">
                  <option value="">Select parent category (optional)</option>
                  {#each mainCategories as category}
                    <option value={category.name}>{category.name}</option>
                  {/each}
                </select>
              </div>
              <button type="submit" class="w-full rounded bg-blue-600 py-2 px-4 text-white hover:bg-blue-700">Add Category</button>
            </form>
          </div>
        </section>

        <section class="bg-white shadow rounded-lg p-6 space-y-6">
          <div>
            <h2 class="text-xl font-semibold mb-4">Categories</h2>
            <p class="text-sm text-gray-600 mb-6">Manage the dashboard categories and their positions.</p>
            <div class="space-y-4">
              {#each mainCategories as category}
                <div class="rounded-xl border border-gray-200 bg-gray-50 p-4">
                  <div class="flex items-center justify-between gap-4">
                    <div>
                      <p class="font-semibold text-gray-900">{category.name}</p>
                    </div>
                    <div class="flex flex-wrap gap-2">
                      <form action="?/up" method="POST" class="inline">
                        <input type="hidden" name="category" value={category.name}>
                        <button class="rounded bg-white px-3 py-2 text-sm text-blue-600 hover:bg-blue-50">Up</button>
                      </form>
                      <form action="?/down" method="POST" class="inline">
                        <input type="hidden" name="category" value={category.name}>
                        <button class="rounded bg-white px-3 py-2 text-sm text-blue-600 hover:bg-blue-50">Down</button>
                      </form>
                      <form action="?/deleteCategory" method="POST" class="inline">
                        <input type="hidden" name="category" value={category.name}>
                        <button class="rounded bg-red-50 px-3 py-2 text-sm text-red-600 hover:bg-red-100">Delete</button>
                      </form>
                    </div>
                  </div>

                  {#if getSubcategories(category.name).length > 0}
                    <div class="mt-4 space-y-3 rounded-lg bg-white p-4">
                      {#each getSubcategories(category.name) as subcategory}
                        <div class="flex items-center justify-between gap-4 rounded-lg border border-gray-200 bg-gray-50 p-3">
                          <span class="text-sm text-gray-700">↳ {subcategory.name}</span>
                          <div class="flex gap-2">
                            <form action="?/up" method="POST" class="inline">
                              <input type="hidden" name="category" value={subcategory.name}>
                              <button class="rounded bg-white px-3 py-2 text-sm text-blue-600 hover:bg-blue-50">Up</button>
                            </form>
                            <form action="?/down" method="POST" class="inline">
                              <input type="hidden" name="category" value={subcategory.name}>
                              <button class="rounded bg-white px-3 py-2 text-sm text-blue-600 hover:bg-blue-50">Down</button>
                            </form>
                            <form action="?/deleteCategory" method="POST" class="inline">
                              <input type="hidden" name="category" value={subcategory.name}>
                              <button class="rounded bg-red-50 px-3 py-2 text-sm text-red-600 hover:bg-red-100">Delete</button>
                            </form>
                          </div>
                        </div>
                      {/each}
                    </div>
                  {/if}
                </div>
              {/each}
            </div>
          </div>
        </section>
      </div>
    </main>
</div>