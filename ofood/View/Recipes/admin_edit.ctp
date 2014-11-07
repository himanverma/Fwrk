<div class="recipes form">
<?php echo $this->Form->create('Recipe'); ?>
	<fieldset>
		<legend><?php echo __('Admin Edit Recipe'); ?></legend>
	<?php
		echo $this->Form->input('id');
		echo $this->Form->input('recipe_name');
		echo $this->Form->input('description');
		echo $this->Form->input('also_known_as');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $this->Form->value('Recipe.id')), array(), __('Are you sure you want to delete # %s?', $this->Form->value('Recipe.id'))); ?></li>
		<li><?php echo $this->Html->link(__('List Recipes'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Combination Items'), array('controller' => 'combination_items', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination Item'), array('controller' => 'combination_items', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Meal Menus'), array('controller' => 'meal_menus', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Meal Menu'), array('controller' => 'meal_menus', 'action' => 'add')); ?> </li>
	</ul>
</div>
