<script lang="ts">
    const { data } = $props();
    const { dishes, BASE_URL_BACKEND } = data;

    let currentPage = $state(0);

    async function nextPage() {
        try {
            const response = await fetch(BASE_URL_BACKEND + '/dishes');
            if (!response.ok) {
                throw new Error(`Failed to fetch dishes: ${response.statusText}`);
            }

            const dishes = await response.json();
            return {
                dishes
            };
        } catch (error) {
            console.error(error);
            return {
                error: 'Failed to load dishes'
            };
        }
    }

    async function prevPage() {
        try {
            const response = await fetch(BASE_URL_BACKEND + '/dishes');
            if (!response.ok) {
                throw new Error(`Failed to fetch dishes: ${response.statusText}`);
            }

            const dishes = await response.json();
            return {
                dishes
            };
        } catch (error) {
            console.error(error);
            return {
                error: 'Failed to load dishes'
            };
        }
    }
    let reversedDishes = dishes.reverse();
</script>

<div class="container px-4 w-full">
    <div class="grid lg:grid-cols-4 md:grid-cols-3 sm:grid-cols-2 xs:grid-cols-1 gap-10">
    {#each reversedDishes as { id, name, imageName }}
        <div class="overflow-hidden rounded-lg shadow-lg relative">
            <a aria-label="Link" href="/dishes/{id}" class="block">
                <div class="h-60 w-full bg-cover bg-center" 
                        style="background-image: url('{`/file/${imageName}`}');">
                </div>
                <div class="bg-[rgb(96,110,90)] p-4 text-white absolute bottom-0 w-full">
                    <h2 class="text-lg font-bold">
                        {name}
                    </h2>
                </div>
            </a>
        </div>
    {/each}
    </div>
</div>
<div class="justify-center flex">
    <button onclick={() => prevPage()} class="cursor-pointer text-white bg-[rgb(96,110,90)] pl-10 pr-10 pt-5 pb-5 hover:bg-[rgb(96,110,0)]">Previous page</button>
    <button onclick={() => nextPage()} class="cursor-pointer text-white bg-[rgb(96,110,90)] pl-10 pr-10 pt-5 pb-5 hover:bg-[rgb(96,110,0)]">Next page</button>
</div>