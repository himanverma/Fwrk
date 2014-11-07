<div class="combinations form">
<?php echo $this->Form->create('Combination'); ?>
	<fieldset>
		<legend><?php echo __('Add Combination'); ?></legend>
	<?php
		echo $this->Form->input('vendor_id');
		echo $this->Form->input('display_name');
		echo $this->Form->input('day');
		echo $this->Form->input('date');
		echo $this->Form->input('price');
		echo $this->Form->input('status');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Html->link(__('List Combinations'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Vendors'), array('controller' => 'vendors', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor'), array('controller' => 'vendors', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Combination Items'), array('controller' => 'combination_items', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination Item'), array('controller' => 'combination_items', 'action' => 'add')); ?> </li>
	</ul>
</div>
