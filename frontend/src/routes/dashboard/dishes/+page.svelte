<script lang="ts">
  const { data, form } = $props();
  const { username, role, categories } = $derived(data);

  import type { Category, Dish } from '$lib/types/types.js';

  let mainCategories = $derived(categories.filter((cat: Category) => !cat.parentCategory));
  let getSubcategories = (parentCat: string) => {
    return categories.filter((cat: Category) =>
      cat.parentCategory && cat.parentCategory.name === parentCat
    );
  };

  function dishHasCategory(dish: Dish, category: Category) {
    return dish.categories.some((cat: Category) => cat.name === category.name);
  }

  function getDishDescription(dish: Dish) {
    return dish.description?.trim() || 'No description available.';
  }

  let pageData = $state<any>(data.pageObject);
  let loading = $state(false);

  async function loadPage(p: number) {
    loading = true;
    const res = await fetch(`/api/dishes?page=${p}`);
    pageData = await res.json();
    loading = false;
  }
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
                <a href="/dashboard/categories" 
                    class="px-4 py-2 text-blue-600 hover:text-blue-800 font-medium">
                    Categories
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
            <h2 class="text-xl font-semibold mb-3">Add a Dish</h2>
            {#if form?.dishError}
              <p class="text-red-600 bg-red-50 border border-red-200 rounded p-3 mb-4">{form.dishError}</p>
            {/if}
            <form method="POST" action="?/add" class="space-y-4" enctype="multipart/form-data">
              <div>
                <label class="block text-sm font-medium text-gray-700" for="dish-name">Dish name</label>
                <input id="dish-name" name="name" type="text" required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700" for="dish-description">Description</label>
                <textarea id="dish-description" name="description" rows="3" required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500"></textarea>
              </div>
              <div>
                <fieldset class="space-y-2">
                  <legend class="block text-sm font-medium text-gray-700">Categories</legend>
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
                </fieldset>
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700" for="dish-image">Image</label>
                <input id="dish-image" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" 
                  type="file" accept="image/*" name="image" />
              </div>
              <button type="submit" class="w-full rounded bg-blue-600 py-2 px-4 text-white hover:bg-blue-700">Add Dish</button>
            </form>
          </div>
        </section>

        <section class="space-y-6">
          <div class="bg-white shadow rounded-lg p-6">
            <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4">
              <div>
                <h2 class="text-xl font-semibold">Dishes</h2>
                <p class="text-sm text-gray-600">Browse and manage dishes from the dashboard.</p>
              </div>
            </div>
          </div>

          <div class="grid gap-6 lg:grid-cols-2">
            {#if loading}
              <div class="col-span-full rounded-lg bg-white shadow p-6 text-center text-gray-600">Loading dishes…</div>
            {:else if pageData?.content?.length}
              {#each pageData.content as dish}
                <article class="bg-white shadow rounded-lg overflow-hidden">
                  <a href={`/dishes/${dish.id}`} class="block">
                    <img
                      class="w-full h-72 object-cover"
                      src={`/file/${dish.imageName}`}
                      alt={dish.name}
                      loading="lazy"
                    />
                  </a>
                  <div class="p-5">
                    <div class="flex flex-wrap gap-2 mb-3">
                      {#each dish.categories as category}
                        <span class="text-xs font-semibold uppercase tracking-wide bg-blue-50 text-blue-700 px-2 py-1 rounded-full">{category.name}</span>
                      {/each}
                    </div>
                    <h3 class="text-lg font-semibold text-gray-900 mb-2">{dish.name}</h3>
                    <div class="flex flex-col gap-3">
                      <div class="flex items-center gap-3">
                        <form action="?/deleteDish" method="POST" class="inline">
                          <input type="hidden" name="id" value={dish.id} />
                          <input type="hidden" name="imageName" value={dish.imageName} />
                          <button type="submit" class="rounded bg-red-50 px-3 py-2 text-sm text-red-600 hover:bg-red-100">Delete</button>
                        </form>
                      </div>
                      <details class="rounded-lg bg-gray-50 border border-gray-200 p-3">
                        <summary class="cursor-pointer rounded-md bg-gray-100 px-3 py-2 text-sm font-medium text-gray-700 hover:bg-gray-200">Edit dish</summary>
                        <div class="mt-4 space-y-4">
                          <form method="POST" action="?/updateDish" class="space-y-4" enctype="multipart/form-data">
                            <input type="hidden" name="id" value={dish.id} />
                            <input type="hidden" name="existingImageName" value={dish.imageName} />
                            <div>
                              <label class="block text-sm font-medium text-gray-700" for="dish-name-{dish.id}">Dish name</label>
                              <input id="dish-name-{dish.id}" name="name" type="text" value={dish.name} required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" />
                            </div>
                            <div>
                              <label class="block text-sm font-medium text-gray-700" for="dish-description-{dish.id}">Description</label>
                              <textarea id="dish-description-{dish.id}" name="description" rows="3" required class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500">{getDishDescription(dish)}</textarea>
                            </div>
                            <div>
                              <fieldset class="space-y-2">
                                <legend class="block text-sm font-medium text-gray-700">Categories</legend>
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
                              </fieldset>
                            </div>
                            <div>
                              <label class="block text-sm font-medium text-gray-700" for="dish-image-{dish.id}">Replace image</label>
                              <input id="dish-image-{dish.id}" class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-blue-500 focus:ring-blue-500" type="file" accept="image/*" name="image" />
                            </div>
                            <button type="submit" class="rounded bg-blue-600 px-3 py-2 text-sm text-white hover:bg-blue-700">Save changes</button>
                          </form>
                        </div>
                      </details>
                    </div>
                  </div>
                </article>
              {/each}
            {:else}
              <div class="col-span-full rounded-lg bg-white shadow p-6 text-center text-gray-600">No dishes found.</div>
            {/if}
          </div>

          <div class="flex flex-wrap items-center justify-between gap-3 bg-white shadow rounded-lg p-6">
            <span class="text-sm text-gray-700">Page {pageData?.number}</span>
            <div class="flex gap-3">
              {#if pageData?.first === false}
                <button
                  class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
                  onclick={() => loadPage((pageData.number ?? 0) - 1)}>
                  Vorige
                </button>
              {/if}
              {#if pageData?.last === false}
                <button
                  class="rounded bg-gray-200 px-4 py-2 hover:bg-gray-300"
                  onclick={() => loadPage((pageData.number ?? 0) + 1)}>
                  Volgende
                </button>
              {/if}
            </div>
          </div>
        </section>
      </div>
    </main>
</div>
