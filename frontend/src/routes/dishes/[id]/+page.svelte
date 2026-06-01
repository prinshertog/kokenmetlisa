<script lang="ts">
    import MarkdownIt from "markdown-it";  
    const { data } = $props();
    const { imageName, name, description, categories } = data.dish;
    
    const md = new MarkdownIt(); 
    const html = md.render(description); 

</script>

<div class="max-w-4xl mx-auto py-8 px-4">
    <div class="bg-white shadow rounded-lg overflow-hidden">
        <img class="w-full h-64 object-cover" src='{`/file/full-${imageName}`}' alt={name} />
        <div class="p-6">
            <h1 class="text-3xl font-bold mb-4">{name}</h1>
            <div class="prose prose-neutral max-w-none">
                {@html html}
            </div>
            <div class="mt-4 text-sm text-gray-500">
                <p class="mt-4 text-sm text-gray-500">Categories:</p>
                <div class="flex overflow-auto">
                    {#each categories as category}
                    <p class="m-1">{category.category}</p>
                    {/each}
                </div>
            </div>
        </div>
    </div>
</div>