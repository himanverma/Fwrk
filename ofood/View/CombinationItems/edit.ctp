<div class="combinationItems form">
<?php echo $this->Form->create('CombinationItem'); ?>
	<fieldset>
		<legend><?php echo __('Edit Combination Item'); ?></legend>
	<?php
		echo $this->Form->input('id');
		echo $this->Form->input('combination_id');
		echo $this->Form->input('recipe_id');
		echo $this->Form->input('name_to_display');
		echo $this->Form->input('plate_size');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Form->postLink(__('Delete'), array('action' => 'delete', $this->Form->value('CombinationItem.id')), array(), __('Are you sure you want to delete # %s?', $this->Form->value('CombinationItem.id'))); ?></li>
		<li><?php echo $this->Html->link(__('List Combination Items'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Combinations'), array('controller' => 'combinations', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination'), array('controller' => 'combinations', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Recipes'), array('controller' => 'recipes', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Recipe'), array('controller' => 'recipes', 'action' => 'add')); ?> </li>
	</ul>
</div>
