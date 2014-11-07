<div class="vendors form">
<?php echo $this->Form->create('Vendor'); ?>
	<fieldset>
		<legend><?php echo __('Add Vendor'); ?></legend>
	<?php
		echo $this->Form->input('name');
		echo $this->Form->input('photo');
		echo $this->Form->input('company_logo');
		echo $this->Form->input('company_name');
		echo $this->Form->input('address');
		echo $this->Form->input('city');
		echo $this->Form->input('state');
		echo $this->Form->input('country');
		echo $this->Form->input('email');
		echo $this->Form->input('password');
		echo $this->Form->input('mobile_number');
		echo $this->Form->input('phone_number');
		echo $this->Form->input('lat');
		echo $this->Form->input('long');
		echo $this->Form->input('status');
	?>
	</fieldset>
<?php echo $this->Form->end(__('Submit')); ?>
</div>
<div class="actions">
	<h3><?php echo __('Actions'); ?></h3>
	<ul>

		<li><?php echo $this->Html->link(__('List Vendors'), array('action' => 'index')); ?></li>
		<li><?php echo $this->Html->link(__('List Combinations'), array('controller' => 'combinations', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Combination'), array('controller' => 'combinations', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Days'), array('controller' => 'vendor_days', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Day'), array('controller' => 'vendor_days', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Documents'), array('controller' => 'vendor_documents', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Document'), array('controller' => 'vendor_documents', 'action' => 'add')); ?> </li>
		<li><?php echo $this->Html->link(__('List Vendor Reviews'), array('controller' => 'vendor_reviews', 'action' => 'index')); ?> </li>
		<li><?php echo $this->Html->link(__('New Vendor Review'), array('controller' => 'vendor_reviews', 'action' => 'add')); ?> </li>
	</ul>
</div>
