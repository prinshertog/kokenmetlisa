<script lang="ts">
  import { env } from '$env/dynamic/public';

  const { data, form } = $props();
  const { username, role, categories } = $derived(data);
  let pageObject = $state(data.pageObject);
  let selectedCategory = $state('');
  let loading = $state(false);

  import type { Category, InspirationDish } from '$lib/types/types.js';

  let mainCategories = $derived(categories.filter((cat: Category) => !cat.parentCategory));

  const BASE_URL_BACKEND = env.PUBLIC_BASE_URL_BACKEND;

  function getSubcategories(parentCat: string) {
    return categories.filter(
      (cat: Category) => cat.parentCategory && cat.parentCategory.name === parentCat
    );
  }

  function dishHasCategory(dish: InspirationDish, category: Category) {
    return dish.categories.some((cat) => cat.name === category.name);
  }

  async function loadPage(page: number) {
    loading = true;
    const categoryQuery = selectedCategory ? `&category=${encodeURIComponent(selectedCategory)}` : '';
    const res = await fetch(`${BASE_URL_BACKEND}/inspirationDishes?page=${page}${categoryQuery}`);
    pageObject = await res.json();
    loading = false;
  }
</script>

<svelte:head>
  <title>Koken met Lisa | Inspiration Dishes</title>
</svelte:head>

<div class="min-h-screen bg-gray-50">
  <!-- Header -->
  <header class="bg-white shadow">
    <div class="max-w-7xl mx-auto py-4 px-4 sm:px-6 lg:px-8 flex justify-between items-center">
      <h1 class="text-2xl font-semibold text-gray-900">Welcome {username}</h1>
      <div class="flex items-center space-x-4">
        {#if role === 'ADMIN'}
          <a href="/users" class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">Users</a>
        {:else}
          <a href="/users" class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">User Settings</a>
        {/if}
        <a href="/dashboard/categories" class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">Categories</a>
        <a href="/dashboard/dishes" class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">Dishes</a>
        <form action="?/logout" method="POST" class="inline">
          <button class="px-4 py-2 text-red-600 hover:text-red-800 font-medium cursor-pointer">Logout</button>
        </form>
      </div>
    </div>
  </header>

  <main class="max-w-7xl mx-auto py-6 sm:px-6 lg:px-8">
    <div class="grid gap-6 lg:grid-cols-[320px_minmax(0,1fr)]">
      <section class="bg-white shadow rounded-lg p-6 space-y-6">
        <div>
          <h2 class="text-xl font-semibold mb-4">Create Inspiration Dish</h2>
          {#if form?.inspirationDishError}
            <p class="text-red-600 bg-red-50 border border-red-200 rounded p-3 mb-4">{form.inspirationDishError}</p>
          {/if}
          {#if form?.inspirationDishSuccess}
            <p class="text-green-700 bg-green-50 border border-green-200 rounded p-3 mb-4">Inspiration dish created successfully.</p>
          {/if}
          <form method="POST" action="?/addInspirationDish" class="space-y-4">
            <div>
              <label class="block text-sm font-medium text-gray-700" for="title">Title</label>
              <input id="title" name="title" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700" for="shortDescription">Short Description</label>
              <textarea id="shortDescription" name="shortDescription" rows="3" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"></textarea>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700" for="imageUrl">Image URL</label>
              <input id="imageUrl" name="imageUrl" type="url" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700">Categories</label>
              <div class="space-y-4 mt-2 max-h-48 overflow-y-auto rounded border border-gray-200 bg-gray-50 p-3">
                {#each mainCategories as category}
                  <div class="rounded-lg bg-white p-3 border border-gray-200">
                    <label class="flex items-center gap-2 text-sm font-semibold text-gray-900">
                      <input type="checkbox" name="selected-categories" value={category.name} class="h-4 w-4 text-blue-600 border-gray-300 rounded" />
                      <span>{category.name}</span>
                    </label>
                    {#each getSubcategories(category.name) as subcategory}
                      <label class="flex items-center gap-2 text-sm text-gray-700 ml-5 mt-2">
                        <input type="checkbox" name="selected-categories" value={subcategory.name} class="h-4 w-4 text-blue-600 border-gray-300 rounded" />
                        <span>{subcategory.name}</span>
                      </label>
                    {/each}
                  </div>
                {/each}
              </div>
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700" for="sourceName">Source Name</label>
              <input id="sourceName" name="sourceName" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
            </div>
            <div>
              <label class="block text-sm font-medium text-gray-700" for="externalUrl">External URL</label>
              <input id="externalUrl" name="externalUrl" type="url" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
            </div>
            <button type="submit" class="w-full rounded bg-blue-600 py-2 px-4 text-white hover:bg-blue-700">Create Inspiration Dish</button>
          </form>
        </div>
      </section>

      <section class="space-y-6">
        <div class="bg-white shadow rounded-lg p-6">
          <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
            <div>
              <h2 class="text-xl font-semibold">Inspiration from Dishes</h2>
              <p class="text-sm text-gray-600">Select a category to filter inspiration dishes.</p>
            </div>
            <div class="flex gap-3 items-center">
              <label class="text-sm font-medium text-gray-700" for="category-select">Filter:</label>
              <select
                id="category-select"
                class="border rounded-md px-3 py-2"
                bind:value={selectedCategory}
                on:change={() => loadPage(0)}>
                <option value="">All categories</option>
                {#each mainCategories as category}
                  <option value={category.name}>{category.name}</option>
                  {#each getSubcategories(category.name) as subcategory}
                    <option value={subcategory.name}>↳ {subcategory.name}</option>
                  {/each}
                {/each}
              </select>
            </div>
          </div>
        </div>

        <div class="grid gap-6 lg:grid-cols-2">
          {#if loading}
            <div class="col-span-full rounded-lg bg-white shadow p-6 text-center text-gray-600">Loading inspiration dishes…</div>
          {:else if pageObject?.content?.length}
            {#each pageObject.content as dish}
              <article class="bg-white shadow rounded-lg overflow-hidden">
                <div class="h-56 bg-gray-100 overflow-hidden">
                  <img
                    src={dish.imageUrl ?? '/placeholder.png'}
                    alt={dish.title}
                    class="w-full h-full object-cover"
                    loading="lazy"
                  />
                </div>
                <div class="p-5">
                  <div class="flex flex-wrap gap-2 mb-3">
                    {#each dish.categories as category}
                      <span class="text-xs font-semibold uppercase tracking-wide bg-blue-50 text-blue-700 px-2 py-1 rounded-full">{category.name}</span>
                    {/each}
                  </div>
                  <h3 class="text-lg font-semibold text-gray-900 mb-2">{dish.title}</h3>
                  <p class="text-sm text-gray-600 mb-4">{dish.shortDescription ?? 'No description available.'}</p>
                  <div class="flex items-center justify-between gap-3">
                    <span class="text-sm text-gray-500">Source: {dish.sourceName}</span>
                    {#if dish.externalUrl}
                      <a href={dish.externalUrl} target="_blank" rel="noopener noreferrer" class="text-blue-600 hover:text-blue-800 text-sm">View</a>
                    {/if}
                  </div>

                  <div class="mt-4 flex flex-wrap gap-2">
                    <form method="POST" action="?/deleteInspirationDish" class="inline">
                      <input type="hidden" name="id" value={dish.id} />
                      <button type="submit" class="rounded bg-red-600 px-3 py-2 text-white hover:bg-red-700">Delete</button>
                    </form>
                    <details class="w-full">
                      <summary class="cursor-pointer rounded bg-gray-100 px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-200">Edit</summary>
                      <div class="mt-3 space-y-4 p-4 border border-gray-200 rounded-lg bg-gray-50">
                        <form method="POST" action="?/updateInspirationDish" class="space-y-4">
                          <input type="hidden" name="id" value={dish.id} />
                          <div>
                            <label class="block text-sm font-medium text-gray-700" for="title-{dish.id}">Title</label>
                            <input id="title-{dish.id}" name="title" value={dish.title} required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
                          </div>
                          <div>
                            <label class="block text-sm font-medium text-gray-700" for="shortDescription-{dish.id}">Short Description</label>
                            <textarea id="shortDescription-{dish.id}" name="shortDescription" rows="3" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">{dish.shortDescription}</textarea>
                          </div>
                          <div>
                            <label class="block text-sm font-medium text-gray-700" for="imageUrl-{dish.id}">Image URL</label>
                            <input id="imageUrl-{dish.id}" name="imageUrl" type="url" value={dish.imageUrl} class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
                          </div>
                          <div>
                            <label class="block text-sm font-medium text-gray-700">Categories</label>
                            <div class="space-y-4 mt-2 max-h-40 overflow-y-auto rounded border border-gray-200 bg-white p-3">
                              {#each mainCategories as category}
                                <div class="rounded-lg border border-gray-200 bg-gray-50 p-3">
                                  <label class="flex items-center gap-2 text-sm font-semibold text-gray-900">
                                    <input type="checkbox" name="selected-categories" value={category.name} checked={dishHasCategory(dish, category)} class="h-4 w-4 text-blue-600 border-gray-300 rounded" />
                                    <span>{category.name}</span>
                                  </label>
                                  {#each getSubcategories(category.name) as subcategory}
                                    <label class="flex items-center gap-2 text-sm text-gray-700 ml-5 mt-2">
                                      <input type="checkbox" name="selected-categories" value={subcategory.name} checked={dishHasCategory(dish, subcategory)} class="h-4 w-4 text-blue-600 border-gray-300 rounded" />
                                      <span>{subcategory.name}</span>
                                    </label>
                                  {/each}
                                </div>
                              {/each}
                            </div>
                          </div>
                          <div>
                            <label class="block text-sm font-medium text-gray-700" for="sourceName-{dish.id}">Source Name</label>
                            <input id="sourceName-{dish.id}" name="sourceName" value={dish.sourceName} required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
                          </div>
                          <div>
                            <label class="block text-sm font-medium text-gray-700" for="externalUrl-{dish.id}">External URL</label>
                            <input id="externalUrl-{dish.id}" name="externalUrl" type="url" value={dish.externalUrl} class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
                          </div>
                          <button type="submit" class="rounded bg-blue-600 px-3 py-2 text-white hover:bg-blue-700">Save changes</button>
                        </form>
                      </div>
                    </details>
                  </div>
                </div>
              </article>
            {/each}
          {:else}
            <div class="col-span-full rounded-lg bg-white shadow p-6 text-center text-gray-600">No inspiration dishes found.</div>
          {/if}
        </div>

        <div class="flex flex-wrap items-center justify-between gap-3 bg-white shadow rounded-lg p-6">
          <span class="text-sm text-gray-700">Page {pageObject?.number ?? 0}</span>
          <div class="flex gap-3">
            {#if pageObject?.first === false}
              <button
                class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
                on:click={() => loadPage((pageObject.number ?? 0) - 1)}>
                Previous
              </button>
            {/if}
            {#if pageObject?.last === false}
              <button
                class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
                on:click={() => loadPage((pageObject.number ?? 0) + 1)}>
                Next
              </button>
            {/if}
          </div>
        </div>
      </section>
    </div>
  </main>
</div>